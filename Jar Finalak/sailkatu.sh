java -jar SVMsailkatzailea.jar ./svm.mdl ./diabetes.arff ./emaitzak.arff prediccion_svm
java -jar one_class_klasifikatu.jar ./one_class.mdl ./diabetes.arff ./emaitzak.arff prediccion_oneclass
java -jar sailkatzefasea.jar ./naivebayes.mdl ./diabetes.arff ./emaitzak.arff prediccion_naivebayes
java -jar sailkatzefasea.jar ./bayesnet.mdl ./diabetes.arff ./emaitzak.arff prediccion_bayesnet

