echo "Running Hadoop Job %1.%2"
echo "loading input file from local" && ^
hadoop fs -rm /bdms/inputs/%1/%2.txt && ^
hadoop fs -mkdir /bdms/inputs/%1 && ^
hadoop fs -put ./inputs/%1/%2.txt /bdms/inputs/%1/%2.txt &&^
echo "Running Jar" && ^
hadoop jar ./MapReduce.jar %1.%2 /bdms/inputs/%1 /bdms/outputs/%1 &&^
echo "Clearning local output folder" && ^
rmdir /s /q "./outputs/%1/%2" &^
echo "Copy results from HDFS" && ^
mkdir outputs\%1 & ^
hadoop fs -get /bdms/outputs/%1/%2 ./outputs/%1 && ^
echo "Renaming output file ./outputs/%1/%2/part-r-00000" &&^
cd outputs/%1/%2/ && ren part-r-00000 out.txt &&^
echo "Deleting SUCCESS" &&^
del _SUCCESS &&^
echo "Open In VSCode" &&^
code out.txt &&^
cd ../../..