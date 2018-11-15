javac -cp src/main/java/ -d out src/main/java/fi/utu/fractalexplorer/TestPerf.java

# returns non-zero on failure. connect to ci pipeline
java -cp out fi.utu.fractalexplorer.TestPerf
