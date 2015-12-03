package edu.csulb.android.mortgagecalculator;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private EditText _LoanAmount;
	private SeekBar _InterestRate;
	private RadioGroup _RadioGroup;
	private RadioButton _LoanTerm15, _LoanTerm20, _LoanTerm30;
	private CheckBox _TaxInsurance;
	private TextView _MonthlyPay, _SeekValue;

	float _fLoanAmount, _fInterestRate, _fMonthlyPay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_LoanAmount = (EditText) findViewById(R.id.txtLoanAmt);
		_InterestRate = (SeekBar) findViewById(R.id.seekIntRate);
		_RadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		_LoanTerm15 = (RadioButton) findViewById(R.id.radio15);
		_LoanTerm20 = (RadioButton) findViewById(R.id.radio20);
		_LoanTerm30 = (RadioButton) findViewById(R.id.radio30);
		_TaxInsurance = (CheckBox) findViewById(R.id.chkTaxInsu);
		_MonthlyPay = (TextView) findViewById(R.id.viewMonthlyPay);
		_SeekValue = (TextView) findViewById(R.id.seekValue);

		_LoanAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				_fInterestRate = Float.parseFloat(_SeekValue.getText()
						.toString());
				calculate();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (_LoanAmount.length() == 0) {
					_MonthlyPay.setText("..................");
				}
			}
		});
		_InterestRate.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				float svalue = (float) ((float) progress / 10.0);
				_SeekValue.setText(String.valueOf(svalue));
				_fInterestRate = Float.parseFloat(_SeekValue.getText()
						.toString());
				calculate();
			}
		});

		_RadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				_fInterestRate = Float.parseFloat(_SeekValue.getText()
						.toString());
				calculate();
			}
		});

		_TaxInsurance
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						_fInterestRate = Float.parseFloat(_SeekValue.getText()
								.toString());
						calculate();
					}
				});

	}

	@SuppressWarnings("deprecation")
	public void calculate() {

		// Alert Dialog
		final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
				.create();
		alertDialog.setTitle("Alert");
		alertDialog.setButton2("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
			}
		});
		
		int checkRadioButtonId = _RadioGroup.getCheckedRadioButtonId();
		_LoanAmount = (EditText) findViewById(R.id.txtLoanAmt);

		try {
//			if (_LoanAmount.length() == 0) {
//				alertDialog.setMessage("Please Enter the Loan Amount.!!");
//				alertDialog.show();
//				_MonthlyPay.setText("..................");
//				_InterestRate.setProgress(100);
//				_LoanTerm15.setChecked(true);
//				_TaxInsurance.setChecked(false);
//			} else {
				if (_fInterestRate == 0.0) {
					switch (checkRadioButtonId) {
					case R.id.radio15:
						if (_LoanTerm15.isChecked()) {
							isInterestZero(15);
						}
					case R.id.radio20:
						if (_LoanTerm20.isChecked()) {
							isInterestZero(20);
						}
					case R.id.radio30:
						if (_LoanTerm30.isChecked()) {
							isInterestZero(30);
						}
					}
				} else {
					switch (checkRadioButtonId) {
					case R.id.radio15:
						if (_LoanTerm15.isChecked()) {
							isInterestNOTZero(15);
						}
					case R.id.radio20:
						if (_LoanTerm20.isChecked()) {
							isInterestNOTZero(20);
						}
					case R.id.radio30:
						if (_LoanTerm30.isChecked()) {
							isInterestNOTZero(30);
						}
					}
				}
//		}
		} catch (Exception e) {
			
		}
	}

	public void isInterestZero(int years) {
		if (_TaxInsurance.isChecked()) {
			_fMonthlyPay = (_fLoanAmount / (years * 12))
					+ (_fLoanAmount / 1000);
			_MonthlyPay.setText(String.valueOf(_fMonthlyPay));
		} else {
			_fMonthlyPay = (_fLoanAmount / (years * 12));
			_MonthlyPay.setText(String.valueOf(_fMonthlyPay));
		}

	}

	public void isInterestNOTZero(int nyears) {
		if (_TaxInsurance.isChecked()) {
			_fLoanAmount = Float.parseFloat(_LoanAmount.getText().toString());
			_fInterestRate = _fInterestRate / 1200;

			_fMonthlyPay = (float) (_fLoanAmount
					* (_fInterestRate / (1 - Math.pow((1 + _fInterestRate),
							(-(nyears * 12))))) + (_fLoanAmount / 1000));
			_MonthlyPay.setText(String.valueOf(_fMonthlyPay));

		} else {
			_fLoanAmount = Float.parseFloat(_LoanAmount.getText().toString());
			_fInterestRate = _fInterestRate / 1200;

			_fMonthlyPay = (float) (_fLoanAmount * (_fInterestRate / (1 - Math
					.pow((1 + _fInterestRate), (-(nyears * 12))))));
			_MonthlyPay.setText(String.valueOf(_fMonthlyPay));

		}
	}

}