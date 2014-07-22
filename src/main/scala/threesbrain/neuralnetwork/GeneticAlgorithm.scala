package threesbrain.neuralnetwork

import scala.annotation.tailrec
import scala.util.Random

object GeneticAlgorithm {
    val mutationRate = 0.1
    val maxMutationPerturbation = 0.3
    val crossOverRate = 0.7
    val eliteProportion = 0.1
    val populationSize = 100
    val numGenerations = 1000

    type Genome = List[Double]

    def train(scoreFun: (NeuralNetwork) => Double,
              layerSizes: List[Int]): NeuralNetwork = {
        assert(populationSize > 0)

        def nextGeneration(population: List[Genome]): List[Genome] = {
            val scores = population.map(genome => scoreFun(NeuralNetwork.fromWeights(layerSizes, genome)))

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
                }
                else
                    List(mom, dad)
            }

            // Roulette-wheel selection
            def pickParent() = {
                def pick(pop: List[Genome], scores: List[Double], num: Double): Genome = {
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
                println(n + " generations left")
                trainRec(nextGeneration(population), n - 1)
        }

        val numWeights = NeuralNetwork.weightLengths(layerSizes).sum
        def randomGenome() = List.fill(numWeights)(Random.nextDouble() * 2.0 - 1.0)
        val startPopulation = List.fill(populationSize)(randomGenome())

        val weights = trainRec(startPopulation, numGenerations).maxBy(
            genome => scoreFun(NeuralNetwork.fromWeights(layerSizes, genome))
        )
        NeuralNetwork.fromWeights(layerSizes, weights)
    }
}
