package engineering_thesis_project.android.ui.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import engineering_thesis_project.android.network.manager.ConnectionManager;
import engineering_thesis_project.android.network.manager.NetworkManager;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.ui.activities.MainActivity_;
import engineering_thesis_project.android.ui.activities.R;
import engineering_thesis_project.android.ui.activities.StartActivity;

@EFragment(R.layout.fragment_wifi_setup)
public class WiFiSetupFragment extends MyFragment {
	@ViewById(R.id.portvalEditText)
	EditText portET;

	@ViewById(R.id.hostip1EditText)
	EditText ip1ET;
	@ViewById(R.id.hostip2EditText)
	EditText ip2ET;
	@ViewById(R.id.hostip3EditText)
	EditText ip3ET;
	@ViewById(R.id.hostip4EditText)
	EditText ip4ET;

	@ViewById(R.id.connectButton)
	Button connect;
	@ViewById(R.id.detectButton)
	Button detect;
	@ViewById(R.id.tryConnectionButton)
	Button tryConButton;
	@ViewById(R.id.progressBar1)
	ProgressBar progress;

	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.WIFI;
		progress.setVisibility(View.INVISIBLE);
		connect.setEnabled(false);
	}

	@Click(R.id.tryConnectionButton)
	public void tryConnection() {
		new Connection().execute();
		// getActivity().setRequestedOrientation(orientation);
	}

	@Click(R.id.connectButton)
	public void connectClick() {
		Intent intent = new Intent(getActivity(), MainActivity_.class);
		intent.putExtra(Constants.CONNECTION_TYPE, Constants.WIFI);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_right,
				R.anim.out_left);
		getActivity().finish();
	}

	@Click(R.id.detectButton)
	public void detectClick() {
		if (ConnectionManager.isWiFiConnected(getActivity())) {
			String[] ip = ConnectionManager.getIpAddr(getActivity()).split(
					"\\.");
			ip1ET.setText(ip[0]);
			ip2ET.setText(ip[1]);
			ip3ET.setText(ip[2]);
			ip4ET.setText(ip[3]);
		} else {
			new AlertDialog.Builder(getActivity())
					.setTitle(R.string.error)
					.setMessage(R.string.no_nettwork)
					.setNeutralButton(android.R.string.ok,
							new OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// nothing
								}
							}).show();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		hideKeyboard();
	}

	public void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(portET.getWindowToken(), 0);
	}

	private class Connection extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			getActivity().setRequestedOrientation(
					ActivityInfo.SCREEN_ORIENTATION_LOCKED);
			progress.setVisibility(View.VISIBLE);
			NetworkManager.setPORT(Integer
					.parseInt(portET.getText().toString()));
			NetworkManager.setSERVER_IP(ip1ET.getText().toString() + "."
					+ ip2ET.getText().toString() + "."
					+ ip3ET.getText().toString() + "."
					+ ip4ET.getText().toString());
			enableComponents(false);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			int counter = 0;
			while (NetworkManager.getInstance().getSocket() == null
					&& counter < 100) {
				try {
					counter++;
					Thread.sleep(15);
				} catch (InterruptedException e) {
					return null;
				}
			}
			return NetworkManager.getInstance().getSocket() != null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				progress.setVisibility(View.INVISIBLE);
				((StartActivity) getActivity()).disableSwipe();
				connect.setEnabled(true);
			} else {
				enableComponents(true);
				progress.setVisibility(View.INVISIBLE);
				NetworkManager.nullInstance();

				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.error)
						.setMessage(R.string.connection_error)
						.setNeutralButton(android.R.string.ok,
								new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
				getActivity().setRequestedOrientation(
						ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
		}
	}

	private void enableComponents(boolean enable) {
		tryConButton.setEnabled(enable);
		portET.setEnabled(enable);
		ip1ET.setEnabled(enable);
		ip2ET.setEnabled(enable);
		ip3ET.setEnabled(enable);
		ip4ET.setEnabled(enable);
		detect.setEnabled(enable);
	}
}
