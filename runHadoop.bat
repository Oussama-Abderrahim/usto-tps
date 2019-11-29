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
hadoop fs -get /bdms/outputs/%1/%2 ./outputs/%1 &&^
code ./outputs/%1/%2^
