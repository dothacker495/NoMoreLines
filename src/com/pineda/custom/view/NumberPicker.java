package com.pineda.custom.view;

import com.example.objectClass.TableReserve;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * A simple layout group that provides a numeric text area with two buttons to
 * increment or decrement the value in the text area. Holding either button will
 * auto increment the value up or down appropriately.
 * 
 * 
 * 
 */
public class NumberPicker extends LinearLayout {
	private TableReserve tr;
	private final long REPEAT_DELAY = 50;

	private final int ELEMENT_HEIGHT = 60;
	private final int ELEMENT_WIDTH = ELEMENT_HEIGHT; // you're all squares, yo

	private final int MINIMUM = 0;
	private int MAXIMUM = 999;

	public Integer value;

	Button decrement;
	Button increment;
	public EditText valueText;

	private Handler repeatUpdateHandler = new Handler();

	private boolean autoIncrement = false;
	private boolean autoDecrement = false;

	private int multiplier;
	private boolean multiplierIndicator;
	private NumberPicker numberPicker;

	/**
	 * This little guy handles the auto part of the auto incrementing feature.
	 * In doing so it instantiates itself. There has to be a pattern name for
	 * that...
	 * 
	 * 
	 * 
	 */
	class RepetetiveUpdater implements Runnable {
		public void run() {
			if (autoIncrement) {
				increment();
				repeatUpdateHandler.postDelayed(new RepetetiveUpdater(),
						REPEAT_DELAY);
			} else if (autoDecrement) {
				decrement();
				repeatUpdateHandler.postDelayed(new RepetetiveUpdater(),
						REPEAT_DELAY);
			}
		}
	}

	public NumberPicker(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);

		this.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		LayoutParams elementParams = new LinearLayout.LayoutParams(
				ELEMENT_HEIGHT, ELEMENT_WIDTH);

		// init the individual elements
		initDecrementButton(context);
		initValueEditText(context);
		initIncrementButton(context);

		// Can be configured to be vertical or horizontal
		// Thanks for the help, LinearLayout!
		if (getOrientation() == VERTICAL) {
			addView(increment, elementParams);
			addView(valueText, elementParams);
			addView(decrement, elementParams);
		} else {
			addView(decrement, elementParams);
			addView(valueText, elementParams);
			addView(increment, elementParams);
		}
	}

	public NumberPicker(boolean multiplierIndicator, int multiplier,
			NumberPicker numberPicker, Context context,
			AttributeSet attributeSet) {
		this(context, attributeSet);
		this.multiplier = multiplier;
		this.numberPicker = numberPicker;
		this.multiplierIndicator = multiplierIndicator;
	}

	public void setNumberPicker(boolean multiplierIndicator, int multiplier,
			NumberPicker numberPicker) {
		this.multiplier = multiplier;
		this.numberPicker = numberPicker;
		this.multiplierIndicator = multiplierIndicator;
		tr=TableReserve.getInstance();
	}

	private void multiply() {
		if (multiplierIndicator == true) {
			int temp=value / multiplier;
			if(value>0&&temp==0)
				++temp;
			numberPicker.setValue(temp);
			tr.setPeople(getValue());
			tr.setTables(temp);
			
		} else {
			numberPicker.setValue(value * multiplier);
			tr.setTables(getValue());
			tr.setPeople(getValue()*multiplier);
		}
	}

	private void initIncrementButton(Context context) {
		increment = new Button(context);
		increment.setTextSize(20);
		increment.setText("+");

		// Increment once for a click
		increment.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				increment();
			}
		});

		// Auto increment for a long click
		increment.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				autoIncrement = true;
				repeatUpdateHandler.post(new RepetetiveUpdater());
				return false;
			}
		});

		// When the button is released, if we're auto incrementing, stop
		increment.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP && autoIncrement) {
					autoIncrement = false;
				}
				return false;
			}
		});
	}

	@SuppressLint("UseValueOf")
	private void initValueEditText(Context context) {

		value = new Integer(0);

		valueText = new EditText(context);
		valueText.setTextSize(20);

		// Since we're a number that gets affected by the button, we need to be
		// ready to change the numeric value with a simple ++/--, so whenever
		// the value is changed with a keyboard, convert that text value to a
		// number. We can set the text area to only allow numeric input, but
		// even so, a carriage return can get hacked through. To prevent this
		// little quirk from causing a crash, store the value of the internal
		// number before attempting to parse the changed value in the text area
		// so we can revert to that in case the text change causes an invalid
		// number
		valueText.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int arg1, KeyEvent event) {
				int backupValue = value;
				try {
					value = Integer.parseInt(((EditText) v).getText()
							.toString());
				} catch (NumberFormatException nfe) {
					value = backupValue;
				}
				return false;
			}
		});

		// Highlight the number when we get focus
		valueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					((EditText) v).selectAll();
				}
			}
		});
		valueText.setGravity(Gravity.CENTER_VERTICAL
				| Gravity.CENTER_HORIZONTAL);
		valueText.setText(value.toString());
		valueText.setInputType(InputType.TYPE_CLASS_NUMBER);
	}

	private void initDecrementButton(Context context) {
		decrement = new Button(context);
		decrement.setTextSize(20);
		decrement.setText("-");

		// Decrement once for a click
		decrement.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				decrement();
			}
		});

		// Auto Decrement for a long click
		decrement.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View arg0) {
				autoDecrement = true;
				repeatUpdateHandler.post(new RepetetiveUpdater());
				return false;
			}
		});

		// When the button is released, if we're auto decrementing, stop
		decrement.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP && autoDecrement) {
					autoDecrement = false;
				}
				return false;
			}
		});
	}

	public void increment() {
		if (value < MAXIMUM) {
			value = value + 1;
			valueText.setText(value.toString());
			multiply();
		}
	}

	public void decrement() {
		if (value > MINIMUM) {
			value = value - 1;
			valueText.setText(value.toString());
			multiply();
		}
	}

	public int getValue() {
		return value;
	}

	public void setMaximum(int maximum) {
		this.MAXIMUM = maximum;
	}

	public void setValue(int value) {
		if (value > MAXIMUM)
			value = MAXIMUM;
		if (value >= 0) {
			this.value = value;
			valueText.setText(this.value.toString());
		}
	}

}