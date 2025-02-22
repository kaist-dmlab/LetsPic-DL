package com.cloudlet.cnnmobilenettrain;

import com.cloudlet.base.android.DfsMapActivity;
import com.cloudlet.base.android.ImageFeatureType;
import com.cloudlet.demo.image_ir.cnn.CnnMobilenetTrainJob;
import com.cloudlet.image_db.ImageDbHelper;
import com.cloudlet.message.internal.DaemonMsgType;
import com.cloudlet.network.NetworkMode;
import com.cloudlet.network.channel.WifiChannelHouse;
import com.cloudlet.network.channel.WifiChannelMonitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageDbClientHelper clientHelper;
	private ImageDbHelper imageDbHelper;

	private Button btnCreateOrEnterGroup;
	private Button btnExitGroup;
	private TextView tvDaemonStatus;
	private TextView tvGroupList;
	private TextView tvJobStatus;
	private Button btnShowDfsInfo;
	private Button btnFormatDfs;
	private Button btnStartJob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// Helper Initialization
		clientHelper = new ImageDbClientHelper(this);
		clientHelper.onCreate(savedInstanceState, getString(R.string.app_name), new CnnMobilenetAndroidInitializer2());
		imageDbHelper = new ImageDbHelper(clientHelper);

		// Connect View
		btnCreateOrEnterGroup = (Button) findViewById(R.id.btnCreateOrEnterGroup);
		btnExitGroup = (Button) findViewById(R.id.btnExitGroup);
		tvDaemonStatus = (TextView) findViewById(R.id.tvDaemonStatus);
		tvGroupList = (TextView) findViewById(R.id.tvGroupList);
		tvJobStatus = (TextView) findViewById(R.id.tvJobStatus);
		btnShowDfsInfo = (Button) findViewById(R.id.btnShowDfsInfo);
		btnFormatDfs = (Button) findViewById(R.id.btnFormatDfs);
		btnStartJob = (Button) findViewById(R.id.btnStartJob);
	}

	@Override
	protected void onStart() {
		super.onStart();
		clientHelper.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		clientHelper.onResume();
	}

	@Override
	protected void onPause() {
		clientHelper.onPause();
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		clientHelper.onBackPressed();
		super.onBackPressed();
	}

	public void onBtnCreateOrEnterGroup(View v) {
		// Start Service
		clientHelper.startDaemon();
	}

	public void onBtnExitGroup(View v) {
		// Terminate Service
		clientHelper.stopDaemon();
	}

	public void onBtnShowDfsInfo(View v) {
		Intent i = new Intent(this, DfsMapActivity.class);
		i.putExtra("dfsMap", clientHelper.dfsMap);
		startActivity(i);
	}

	public void onBtnFormatDfs(View v) {
		clientHelper.format();
	}

	public void onBtnStartJob(View v) {
		clientHelper.sendMsgToDaemon(DaemonMsgType.REQ_START_JOB, new CnnMobilenetTrainJob(3, 11, 5));
	}

	class ImageDbClientHelper extends com.cloudlet.base.android.ImageDbClientHelper {

		public ImageDbClientHelper(MainActivity activity) {
			super(activity, true, NetworkMode.WIFI, WifiChannelMonitor.class, WifiChannelHouse.class,
					ImageFeatureType.CNN_MOBILENET);
		}

		@Override
		public void updateViews() {
			String daemonStatus = "";

			// Different view layouts outputs depending on whether user joins group or not
			if (amIInGroup()) {
				btnCreateOrEnterGroup.setEnabled(false);
				btnExitGroup.setEnabled(true);
				btnStartJob.setEnabled(true);

				daemonStatus += "Join Group : TRUE";

			} else {
				btnCreateOrEnterGroup.setEnabled(true);
				btnExitGroup.setEnabled(false);
				btnStartJob.setEnabled(false);

				daemonStatus += "Join Group : FALSE";
				tvGroupList.setText("");
				tvJobStatus.setText("");
			}

			daemonStatus += " / ";
			if (isDaemonBusy()) {
				daemonStatus += "Daemon BUSY : TRUE";

			} else {
				daemonStatus += "Daemon BUSY : FALSE";
			}
			tvDaemonStatus.setText(daemonStatus);

			if (isDfsInitialized()) {
				btnShowDfsInfo.setEnabled(true);
				btnFormatDfs.setEnabled(true);

			} else {
				btnShowDfsInfo.setEnabled(false);
				btnFormatDfs.setEnabled(false);
			}
		}

		@Override
		public void onGroupListUpdated(String groupList) {
			tvGroupList.setText(groupList);
		}

		@Override
		public void onJobStatusUpdated(String jobStatus) {
			tvJobStatus.setText(jobStatus);
		}

		String dfsMap = "";

		@Override
		public void onDfsMapUpdated(String dfsMap) {
			this.dfsMap = dfsMap;
		}

	}

}
