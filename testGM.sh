cd out/production/LegacyLags/
cp ../../../ORDRES.CSV .
cp ../../../input.txt .
cat input.txt | java -classpath . com.tof.app.Lags > output.txt
diff ../../../GOLDENMASTER.txt output.txt