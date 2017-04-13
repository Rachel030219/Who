/*
 Copyright 2016 Rachel030219

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package net.rachel030219.calltheroll;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;

public class MainActivity extends Activity 
{
	EditText input;
	Button doit;
	Button split;
	TextView result;
	
	boolean clicked = false;
	int now = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		input = (EditText)findViewById(R.id.main_input);
		doit = (Button)findViewById(R.id.main_doit);
		split = (Button)findViewById(R.id.main_split);
		result = (TextView)findViewById(R.id.main_result);
		
		SharedPreferences shared = getSharedPreferences("saved",MODE_PRIVATE);
		String saved = shared.getString("saved",null);
		if(saved != null){
			input.setText(saved);
		}

		doit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(!input.getText().toString().matches("\\s*")){
					final String[] names = input.getText().toString().split(";");
					final Handler handler = new Handler();
					Runnable runnable = new Runnable(){
						@Override
						public void run(){
							if(now < names.length){
								if(clicked){
									result.setText(names[now]);
									now++;
								}
							} else {
								if(clicked)
									now = 0;
							}
							handler.postDelayed(this,20);
						}
					};
					if(!clicked){
						clicked = true;
						doit.setText(R.string.stop);
						handler.post(runnable);
					} else {
						clicked = false;
						doit.setText(R.string.doit);
						handler.removeCallbacksAndMessages(null);
						int value = Math.random(0,names.length);
						result.setText(names[value]);
						now = 0;
					}
				} else {
					result.setText("");
				}
			}
		});
		split.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				int selection = input.getSelectionStart();
				StringBuilder builder = new StringBuilder(input.getText().toString());
				builder.insert(selection,";");
				input.setText(builder.toString());
				input.setSelection(selection + 1);
			}
		});
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode == KeyEvent.KEYCODE_BACK){
			SharedPreferences shared = getSharedPreferences("saved",MODE_PRIVATE);
			shared.edit().putString("saved",input.getText().toString()).apply();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
