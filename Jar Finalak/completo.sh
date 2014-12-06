#!/bin/bash
if [ $# -eq 6 ]
then
        ./modeloak.sh $1 $2 $6
        ./sailkatu.sh $3 $4 $5
elif [ $# -eq 5 ]
then
        ./modeloak.sh $1 $2 $6
        ./sailkatu.sh $3 $4 $5
else
        echo ""
	echo "Parametros necesarios:"
	echo ""
	echo "1-Path fichero train"
	echo "2-Path fichero dev"
        echo "3-Path fichero test"
	echo "4-Path fichero donde guardar los resultados intermedios. Un arff con los votos de cada clasificador"
	echo "5-Path fichero texto plano donde se guardara el resultado final teniendo en cuenta todos los clasificadores"
	echo "6-(opcional) Posicion de la clase. Si no se especifica se utilizara el ultimo atributo como clase"
	echo ""
	echo "Se crearan en la misma carpeta: "
	echo "-Los modelos con los parametros adecuados"
	echo "-El fichero despues de hacer resample con el nombre resampled.arff"
	echo ""
fi
