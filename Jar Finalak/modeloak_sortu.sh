java -jar resamplea.jar ./diabetes.arff -1 ./train_txikia.arff
java -jar SVM.jar ./train_txikia.arff -1 ./diabetes.arff ./svm.mdl
java -jar naivebayes.jar ./train_txikia.arff -1 ./diabetes.arff ./naivebayes.mdl
java -jar bayesNet.jar ./train_txikia.arff -1 ./diabetes.arff ./bayesnet.mdl
java -jar one_class.jar ./train_txikia.arff -1 ./diabetes.arff ./one_class.mdl
