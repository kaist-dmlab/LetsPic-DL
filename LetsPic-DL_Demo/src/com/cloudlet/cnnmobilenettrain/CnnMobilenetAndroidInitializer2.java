package com.cloudlet.cnnmobilenettrain;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.cappuccino.log.Log;
import com.cloudlet.base.IDemoInitializer;
import com.cloudlet.base.IInterface;
import com.cloudlet.base.android.ClientHelper;
import com.cloudlet.demo.image_ir.cnn.CnnMobilenetConst;
import com.cloudlet.demo.image_ir.cnn.CnnMobilenetTrainConst;
import com.cloudlet.environment.LPath;

import android.content.res.AssetManager;

public class CnnMobilenetAndroidInitializer2 implements IDemoInitializer {

	@Override
	public void initialize(IInterface iinterface) {
		AssetManager am = ((ClientHelper) iinterface).getActivity().getAssets();

		placeAssetFile(am, CnnMobilenetTrainConst.CONV_MODEL_FILE_NAME);
		placeAssetFile(am, CnnMobilenetTrainConst.FC_MODEL_FILE_NAME);
		placeAssetFile(am, CnnMobilenetTrainConst.TRAIN_LABELS_FILE_NAME);
		placeAssetFile(am, CnnMobilenetConst.MODEL_FILE_NAME);

		Log.i("CNN 관련 파일 작업공간에 배치 완료");
	}

	private void placeAssetFile(AssetManager am, String fileName) {
		InputStream srcIs = null;
		BufferedOutputStream dstBos = null;
		try {
			srcIs = am.open(fileName);
			dstBos = new BufferedOutputStream(new FileOutputStream(LPath.create(fileName).toFile(), false));

			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = srcIs.read(buffer)) > 0) {
				dstBos.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (srcIs != null) {
				try {
					srcIs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (dstBos != null) {
				try {
					dstBos.flush();
					dstBos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
