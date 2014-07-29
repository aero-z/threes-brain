set datafile separator ","
#set terminal png
#set output 'plot.png'
set key right bottom
plot for [filename in filenames] filename every ::1 using 1:4 with line title filename
pause -1
