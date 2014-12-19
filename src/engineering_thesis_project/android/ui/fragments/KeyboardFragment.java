package engineering_thesis_project.android.ui.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.R.color;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import engineering_thesis_project.android.controlers.keyboard.AWTConstants;
import engineering_thesis_project.android.controlers.keyboard.Keyboard;
import engineering_thesis_project.android.statics.Constants;
import engineering_thesis_project.android.ui.activities.MainActivity;
import engineering_thesis_project.android.ui.activities.R;

@EFragment(R.layout.fragment_keyboard)
public class KeyboardFragment extends MyFragment implements OnClickListener,
		AWTConstants {
	Keyboard keyboard;
	boolean keyboardVisible = true;
	private HashMap<String, Boolean> buttonPressed;
	private ImageView[] f_sButtons = new ImageView[12];

	@ViewById(R.id.invisibleEditText)
	EditText et;

	@ViewById(R.id.ctrlImageView)
	ImageView ctrl;
	@ViewById(R.id.altImageView)
	ImageView altLeft;
	@ViewById(R.id.altGrImageView)
	ImageView altRight;
	@ViewById(R.id.shiftImageView)
	ImageView shift;
	@ViewById(R.id.tabImageView)
	ImageView tab;
	@ViewById(R.id.escImageView)
	ImageView esc;
	@ViewById(R.id.homeImageView)
	ImageView home;
	@ViewById(R.id.endImageView)
	ImageView end;
	@ViewById(R.id.pgUpImageView)
	ImageView pgUp;
	@ViewById(R.id.pgDownImageView)
	ImageView pgDown;
	@ViewById(R.id.insertImageView)
	ImageView insert;
	@ViewById(R.id.deleteImageView)
	ImageView delete;
	@ViewById(R.id.metaImageView)
	ImageView meta;
	@ViewById(R.id.leftImageView)
	ImageView leftArrow;
	@ViewById(R.id.rightImageView)
	ImageView rightArrow;
	@ViewById(R.id.upImageView)
	ImageView upArrow;
	@ViewById(R.id.downImageView)
	ImageView downArrow;

	@ViewById(R.id.toggleButton)
	ImageView toggleKeyboardButton;

	@AfterViews
	public void init() {
		FRAGMENT_TYPE = Constants.KEYBOARD;
		((MainActivity) getActivity()).drawerLyout.setDrawerLockMode(
				DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
				((MainActivity) getActivity()).settingsListView);
		initialiseF_sArray();
		keyboard = new Keyboard();
		et.setAlpha((float) 0);
		et.setText(" ");
		et.setSelection(1);
		et.setBackgroundColor(color.transparent);
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s.length() > 0
						&& s.subSequence(s.length() - 1, s.length()).toString()
								.equalsIgnoreCase("\n")) {
					// enter pressed
					keyboard.onCharButtonPressed('\n');
					et.setText(" ");
				} else if (s.length() == 0) {
					if (someCombinationStared()) {
						int[] ar = getPressedKeys(0);
						keyboard.onCharButtonPressed('\b', ar);
					} else {
						keyboard.onCharButtonPressed('\b');
					}
					et.setText(" ");
				} else if (s.length() == 1) {
					// nothing to do it is neutral state
				} else if (s.length() == 2) {
					if (someCombinationStared()) {
						int[] ar = getPressedKeys(0);
						keyboard.onCharButtonPressed(s.charAt(count), ar);
					} else {
						keyboard.onCharButtonPressed(s.charAt(count));
					}
					et.setText(" ");
				} else {
					Log.e("Impossible state of text edit",
							"charsequence length is more than 2 s=" + s);
					et.setText(" ");
				}
				et.setSelection(1);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		toggleKeyboardButton.setOnClickListener(this);
		toggleKeyboard(true);
		//toggleKeyboard(false);
		setListeners();
		initHashMap();
		setLongClickListeners();
		
	}

	private void initHashMap() {
		buttonPressed = new HashMap<String, Boolean>();
		buttonPressed.put("ctrl", false);
		buttonPressed.put("altRight", false);
		buttonPressed.put("altLeft", false);
		buttonPressed.put("shift", false);
		buttonPressed.put("meta", false);
	}

	public void toggleKeyboard(boolean enable) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (enable) {
			et.requestFocus();
			imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
		} else {
			imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
		}
		keyboardVisible = !keyboardVisible;
	}

	@Override
	public void onClick(View view) {
		toggleKeyboard(keyboardVisible);
	}

	@Override
	public void onPause() {
		super.onPause();
		((MainActivity) getActivity()).drawerLyout.setDrawerLockMode(
				DrawerLayout.LOCK_MODE_UNLOCKED,
				((MainActivity) getActivity()).settingsListView);
		toggleKeyboard(false);
	}

	private void initialiseF_sArray() {
		f_sButtons[0] = (ImageView) getActivity()
				.findViewById(R.id.f1ImageView);
		f_sButtons[1] = (ImageView) getActivity()
				.findViewById(R.id.f2ImageView);
		f_sButtons[2] = (ImageView) getActivity()
				.findViewById(R.id.f3ImageView);
		f_sButtons[3] = (ImageView) getActivity()
				.findViewById(R.id.f4ImageView);
		f_sButtons[4] = (ImageView) getActivity()
				.findViewById(R.id.f5ImageView);
		f_sButtons[5] = (ImageView) getActivity()
				.findViewById(R.id.f6ImageView);
		f_sButtons[6] = (ImageView) getActivity()
				.findViewById(R.id.f7ImageView);
		f_sButtons[7] = (ImageView) getActivity()
				.findViewById(R.id.f8ImageView);
		f_sButtons[8] = (ImageView) getActivity()
				.findViewById(R.id.f9ImageView);
		f_sButtons[9] = (ImageView) getActivity().findViewById(
				R.id.f10ImageView);
		f_sButtons[10] = (ImageView) getActivity().findViewById(
				R.id.f11ImageView);
		f_sButtons[11] = (ImageView) getActivity().findViewById(
				R.id.f12ImageView);
	}

	private void setLongClickListeners() {
		ctrl.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				buttonPressed.put("ctrl", !buttonPressed.get("ctrl"));
				ctrl.post(new Runnable() {
					@Override
					public void run() {
						if(buttonPressed.get("ctrl")){
							ctrl.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.ctrl_button_2)));
						} else {
							ctrl.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.ctrl_selector)));
						}
					}
				});
				return true;
			}
		});

		altRight.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				buttonPressed.put("altRight", !buttonPressed.get("altRight"));
				altRight.post(new Runnable() {
					@Override
					public void run() {
						if(buttonPressed.get("altRight")){
							altRight.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.alt_gr_button_2)));
						} else {
							altRight.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.alt_gr_selector)));
						}
					}
				});
				return true;
			}
		});

		altLeft.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				buttonPressed.put("altLeft", !buttonPressed.get("altLeft"));
				altLeft.post(new Runnable() {
					@Override
					public void run() {
						if(buttonPressed.get("altLeft")){
							altLeft.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.alt_button_2)));
						} else {
							altLeft.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.alt_selector)));
						}
					}
				});
				return true;
			}
		});

		shift.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				buttonPressed.put("shift", !buttonPressed.get("shift"));
				shift.post(new Runnable() {
					@Override
					public void run() {
						if(buttonPressed.get("shift")){
							shift.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.shift_button_2)));
						} else {
							shift.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.shift_selector)));
						}
					}
				});
				return true;
			}
		});

		meta.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				buttonPressed.put("meta", !buttonPressed.get("meta"));
				meta.post(new Runnable() {
					@Override
					public void run() {
						if(buttonPressed.get("meta")){
							meta.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.meta_button_2)));
						} else {
							meta.setImageDrawable(getActivity().getResources()
									.getDrawable((R.drawable.meta_selector)));
						}
					}
				});
				return true;
			}
		});
	}

	private boolean someCombinationStared() {
		return buttonPressed.get("ctrl") || buttonPressed.get("shift")
				|| buttonPressed.get("altLeft")
				|| buttonPressed.get("altRight") || buttonPressed.get("meta");
	}

	private int[] getPressedKeys(int additionalSpace) {
		ArrayList<Integer> l = new ArrayList<Integer>();
		if (buttonPressed.get("ctrl"))
			l.add(VK_CONTROL);
		if (buttonPressed.get("shift"))
			l.add(VK_SHIFT);
		if (buttonPressed.get("altRight"))
			l.add(VK_ALT_GRAPH);
		if (buttonPressed.get("altLeft"))
			l.add(VK_ALT);
		if (buttonPressed.get("meta"))
			l.add(VK_WINDOWS);

		// optional additionalspace in array for use outside this method
		int[] a = new int[l.size() + additionalSpace];
		for (int i = 0; i < l.size(); i++)
			a[i] = l.get(i);
		return a;
	}

	private void setListeners() {
		for (int i = 0; i < 12; i++) {
			final int vk_fx = VK_F1 + i;
			f_sButtons[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (someCombinationStared()) {
						int[] ar = getPressedKeys(1);
						ar[ar.length - 1] = vk_fx;
						keyboard.onKeyCombinationPressed(ar);
					} else
						keyboard.onButtonPressed(vk_fx);
				}
			});
		}

		ctrl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ctrl.setImageDrawable(getActivity().getResources().getDrawable(
						(R.drawable.ctrl_selector)));
				keyboard.onButtonPressed(VK_CONTROL);
			}
		});

		altRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				altRight.setImageDrawable(getActivity().getResources().getDrawable(
						(R.drawable.alt_gr_selector)));
				keyboard.onButtonPressed(VK_ALT_GRAPH);
			}
		});

		altLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				altLeft.setImageDrawable(getActivity().getResources().getDrawable(
						(R.drawable.alt_selector)));
				keyboard.onButtonPressed(VK_ALT);
			}
		});

		shift.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				shift.setImageDrawable(getActivity().getResources().getDrawable(
						(R.drawable.shift_selector)));
				keyboard.onButtonPressed(VK_SHIFT);
			}
		});

		meta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				meta.setImageDrawable(getActivity().getResources().getDrawable(
						(R.drawable.meta_selector)));
				keyboard.onButtonPressed(VK_WINDOWS);
			}
		});

		tab.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_TAB;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_TAB);
			}
		});

		esc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_ESCAPE;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_ESCAPE);
			}
		});

		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_HOME;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_HOME);
			}
		});

		end.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_TAB;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_END);
			}
		});

		pgUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_PAGE_UP;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_PAGE_UP);
			}
		});

		pgDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_PAGE_DOWN;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_PAGE_DOWN);
			}
		});

		insert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_INSERT;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_INSERT);
			}
		});

		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_DELETE;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_DELETE);
			}
		});

		rightArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_RIGHT;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_RIGHT);
			}
		});

		leftArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_LEFT;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_LEFT);
			}
		});

		upArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_UP;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_UP);
			}
		});

		downArrow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (someCombinationStared()) {
					int[] ar = getPressedKeys(1);
					ar[ar.length - 1] = VK_DOWN;
					keyboard.onKeyCombinationPressed(ar);
				} else
					keyboard.onButtonPressed(VK_DOWN);
			}
		});
	}

}
