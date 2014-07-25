if [ -z "$1" ]; then
    echo "missing file name argument"
    exit
fi

filenames="$@"
gnuplot -e "filenames='$filenames'" evolution.gnuplot
