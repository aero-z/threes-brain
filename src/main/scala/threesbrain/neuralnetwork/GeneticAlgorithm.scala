package threesbrain.neuralnetwork

import scala.annotation.tailrec
import scala.util.Random
import scala.collection.GenSeq
import java.io.FileWriter

object GeneticAlgorithm {
    val mutationRate = 0.1
    val maxMutationPerturbation = 0.3
    val crossOverRate = 0.7
    val eliteProportion = 0.1
    val populationSize = 1000
    val numGenerations = 300
    
    type Genome = List[Double]

    def train(scoreFun: (NeuralNetwork) => Double,
              layerSizes: List[Int]): NeuralNetwork = {
        assert(populationSize > 0)

        val numWeights = NeuralNetwork.weightLengths(layerSizes).sum
        
        val fileName = s"threesbrain-log-${System.currentTimeMillis()/1000}.csv"
        val log = new FileWriter(fileName)
        log.write("Round,PopBestAvg,PopWorstAvg,PopAvg\n")

        def nextGeneration(population: List[Genome]): List[Genome] = {
            val scores = population.par.map(genome => scoreFun(NeuralNetwork.fromWeights(layerSizes, genome)))
            val (max, min, avg) = (scores.max, scores.min, scores.sum/scores.length)
            log.write(s"$max,$min,$avg\n")
            log.flush
            println(f"\tbest=$max%.2f,\tworst=$min%.2f,\taverage=$avg%.2f")

            // Mutate single weights according to mutation rate
            def mutate(genome: Genome) = genome.map({ w =>
                if (Random.nextDouble() < mutationRate)
                    w + (Random.nextDouble() * 2.0 - 1.0) * maxMutationPerturbation
                else
                    w
            })

            // Create two children and cross over their genomes if the cross-over random variable is active
            def crossover(mom: Genome, dad: Genome) = {
                if (Random.nextDouble() < crossOverRate) {
                    val crossoverPoint = Random.nextInt(mom.length)
                    List(
                        mom.take(crossoverPoint) ::: dad.drop(crossoverPoint),
                        dad.take(crossoverPoint) ::: mom.drop(crossoverPoint)
                    )
                    /* Comment out the following for uniform crossover
                    val flips = List.fill(numWeights)(Random.nextBoolean())
                    val momDadFlips = mom.zip(dad).zip(flips)
                    List(
                        momDadFlips.map{case ((m, d), flip) => if (flip) m else d},
                        momDadFlips.map{case ((m, d), flip) => if (flip) d else m}
                    )
                    */
                }
                else
                    List(mom, dad)
            }

            // Roulette-wheel selection
            def pickParent() = {
                def pick(pop: List[Genome], scores: GenSeq[Double], num: Double): Genome = {
                    if (num < scores.head) pop.head
                    else pick(pop.tail, scores.tail, num - scores.head)
                }
                pick(population, scores, Random.nextInt(scores.sum.toInt))
            }

            def makeOffsprings() = {
                crossover(pickParent(), pickParent()).map(mutate)
            }

            List.fill(population.size / 2)(makeOffsprings()).flatten
        }

        @tailrec
        def trainRec(population: List[Genome], cyclesLeft: Int): List[Genome] = cyclesLeft match {
            case 0 => population
            case n =>
                print(f"${numGenerations - n + 1}%6d/$numGenerations%d: ")
                log.write(numGenerations - n + 1+",")
                trainRec(nextGeneration(population), n - 1)
        }

        def randomGenome() = List.fill(numWeights)(Random.nextDouble() * 2.0 - 1.0)
        val startPopulation = List.fill(populationSize)(randomGenome())

        val weights = trainRec(startPopulation, numGenerations).maxBy(
            genome => scoreFun(NeuralNetwork.fromWeights(layerSizes, genome))
        )
        log.close
        NeuralNetwork.fromWeights(layerSizes, weights)
    }
}
