#!/bin/bash
if [ $# -eq 3 ]
then
	java -jar sailkatzefasea.jar ./random_forest.mdl $1 $2 prediccion_random_forest
	java -jar SVMSailkatzaile.jar ./svm.mdl $1 ./svm.mdl ./svm.mdl.txt prediccion_svm
	java -jar one_class_klasifikatu.jar ./one_class.mdl $1 $2 prediccion_oneclass
	java -jar sailkatzefasea.jar ./naivebayes.mdl $1 $2 prediccion_naivebayes
	java -jar sailkatzefasea.jar ./bayesnet.mdl $1 $2 prediccion_bayesnet
	java -jar emaitzakkudeatu.jar  $2 4 $3
else
	echo ""
	echo "Parametros necesarios:"
	echo ""
	echo "1-Path fichero test"
	echo "2-Path fichero donde guardar los resultados intermedios. Un arff con los votos de cada clasificador"
	echo "2-Path fichero texto plano donde se guardara el resultado final teniendo en cuenta todos los clasificadores"
fi
