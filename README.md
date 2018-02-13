# LetsPic-DL: Distributed Fine-Tuning of CNNsfor Image Retrieval on Multiple Mobile Devices
## 1. Overview
The high performance of mobile devices has enabled deep learningto be extended to also exploit its strengths on such devices. However, because their computing power is not yet sufficient to perform on-device training, a pre-trained model is usually downloaded to mobile devices, and only inference is performed on them. This sit-uation leads to the problem that accuracy may be degraded if the characteristics of the data for training and those for inference are sufficiently different. In general, fine-tuning allows a pre-trained model to adapt to a given data set, but it has also been perceived as difficult on mobile devices. In this paper, we introduce our on-going effort to improve the quality of mobile deep learning by enabling fine-tuning on mobile devices. In order to reduce its cost to a level that can be operated on mobile devices, alight-weight fine-tuning method is proposed, and its cost is further reduced by using dis-tributing computing on mobile devices. The proposed technique has been applied to LetsPic-DL, a group photoware application under development in our research group. It required only 24 seconds to fine-tune a pre-trained MobileNet with 100 photos on five Galaxy S8 units, resulting in an excellent image retrieval accuracy reflected a 27–35% improvement.

## 2. How to configure
	1. Download the source.
	2. Open the 'MainActivity.java'
	3. Set the 'new CnnMobilenetTrainJob(3, 11, 5)' at a 99th line according to your preference.
		- CnnMobilenetTrainJob(Epoch, IterationPerEpoch, NumDevices)
			- Epoch: the number of epoch to finetune the model, which means the number of MapReduce job
			- IterationPerEpoch: the number of iteration per each epoch, which means the number of train dataset stored on a device divided by 15(defualt batch size)
			- NumDevices: the number of devices to finetune the model together
	4. Place train dataset in '/cnnMobilenetTrainDemo/LFS' folder of each device
	5. Run application
	
## 3. How to run 
	1. One user creates Group by clicking 'Create Group/Join Group' button. The other users join the group by clicking the same button.
	2. One of users clicks the 'Start Finetuning' button to finetune MobileNets model.
	
## 4. Data Sets
| Name           | Content  |  # Categories  |# Photos |
| :------------: | :-------:| :------------: |:-------:|
| Cifar100       | various  |       100      | 60,000  | 
| Food-101       |   food   |       101      | 101,000 | 
| Caltech-Faces  |   face   |        27      |   450   | 
