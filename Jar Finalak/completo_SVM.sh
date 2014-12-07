#!/bin/bash
if [ $# -eq 6 ]
then
	./modeloak.sh $1 $2 $6

	#Clasificar

	#SVM

	f='svm.mdl.txt'
	degree=`cut -c 15-16 $f`
	tipo=`cut -c 30-31 $f`
	kernel=`cut -c 47-48 $f`

	java -jar SVMSailkatzaile.jar $1 $degree $kernel $tipo $2 $3 $4 prediccion_svm $6
	java -jar sailkatzefasea.jar ./random_forest.mdl $3 $4 prediccion_random_forest
	java -jar one_class_klasifikatu.jar ./one_class.mdl $3 $4 prediccion_oneclass
	java -jar sailkatzefasea.jar ./naivebayes.mdl $3 $4 prediccion_naivebayes
	java -jar sailkatzefasea.jar ./bayesnet.mdl $3 $4 prediccion_bayesnet
	java -jar emaitzakkudeatu.jar  $4 5 $5
else
	echo ""
	echo "Parametros necesarios:"
	echo ""
	echo "1-Path fichero train"
	echo "2-Path fichero dev"
	echo "3-Path fichero test"
	echo "4-Path fichero donde guardar los resultados intermedios. Un arff con los votos de cada clasificador"
	echo "5-Path fichero texto plano donde se guardara el resultado final teniendo en cuenta todos los clasificadores"
	echo "6-Posicion de la clase"
	echo ""
	echo "Se crearan en la misma carpeta: "
	echo "-Los modelos con los parametros adecuados"
	echo "-El fichero despues de hacer resample con el nombre resampled.arff"
	echo ""
fi
