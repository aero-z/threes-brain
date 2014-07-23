if [ -z "$1" ]; then
    echo "missing file name argument"
    exit
fi

filename=$1
gnuplot -e "filename='$filename'" evolution.gnuplot
