cat $1 | while read class
do
    java ru.ifmo.ctddev.belyy.implementor.Main $class
    javac *.java && echo "OK $class"
    rm -f *.java
    rm -f *.class
done
