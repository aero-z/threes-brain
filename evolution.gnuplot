set datafile separator ","
plot filename every ::1 using 1:4 with line title 'Average score'
pause -1
