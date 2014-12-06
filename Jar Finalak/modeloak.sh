#!/bin/bash
if [ $# -gt 1 ]
then
	if [ $# -eq 2 ]
	then
		klase=-1
	else
		klase=$3
	fi
	java -jar resamplea.jar $1 $klase ./resampled.arff
	java -jar RandomForest.jar ./resampled.arff $klase $2 ./random_forest.mdl
	java -jar SVM.jar ./resampled.arff $klase $2 ./svm.mdl
	java -jar naivebayes.jar ./resampled.arff $klase $2 ./naivebayes.mdl
	java -jar bayesNet.jar ./resampled.arff $klase $2 ./bayesnet.mdl
	java -jar one_class.jar ./resampled.arff $klase $2 ./one_class.mdl
else
	echo ""
	echo "Parametros necesarios:"
	echo ""
	echo "1-Path fichero train"
	echo "2-Path fichero dev"
	echo "3-(opcional) Posicion de la clase. Si no se especifica se utilizara el ultimo atributo como clase"
	echo ""
	echo "Se crearan en la misma carpeta: "
	echo "-Los modelos con los parametros adecuados"
	echo "-El fichero despues de hacer resample con el nombre resampled.arff"
	echo ""
fi
