package com.vu.tab.servy;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

class BackWork extends AsyncTask<String,Void,String> {
    private Context context;

    public BackWork(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];

        if (type.equals("insert")){
            String insert_url = "http://10.44.20.132:8080/srvm/v1/store";

            try {
                String name = params[1];
                String nbfiType = params[2];
                String radioItem = params[3];

                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String post_data = URLEncoder.encode("fullName","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("nbfiType","UTF-8")+"="+URLEncoder.encode(nbfiType,"UTF-8")+"&"
                        +URLEncoder.encode("radioItem","UTF-8")+"="+URLEncoder.encode(radioItem,"UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Insert Successfully!")) {
            //Successfully message
            Toast.makeText(context, "Insert successfully", Toast.LENGTH_SHORT).show();
        } else {
            //Error message
            Toast.makeText(context, "Unsuccessfully", Toast.LENGTH_SHORT).show();
        }
    }
}
