package com.example.xml_reading_example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView txtMsg;
	Button btnGoParser;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtMsg = (TextView) findViewById(R.id.txtMsg);
		btnGoParser = (Button) findViewById(R.id.btnReadXml);

		btnGoParser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				btnGoParser.setEnabled(false);

				// this xml file includes elements: <menu> <food> ...
				String xmlFile = "meal.xml";
				// do slow XML reading in a separated thread
				new backgroundAsyncTask().execute(xmlFile);

			}
		});
	}// onCreate
	
	// /////////////////////////////////////////////////////////////////////////
	private class backgroundAsyncTask extends AsyncTask<String, Void, String> {	// 입력은 String, Progress는 별도로 처리하지 않음, 결과는 String
		ProgressDialog dialog = new ProgressDialog(MainActivity.this);			// Dialog객체를 만들었음

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
			txtMsg.setText(result.toString());
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);
			dialog.show();

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... params) {
			String xmlFileName = params[0];			// Excute에서 파일이름을 받아온다.
			return useW3CParser(xmlFileName);
		}// doInBackground

	}// backgroundAsyncTask

	// ///////////////////////////////////////////////////////////////////
	private String useW3CParser(String xmlFileName) {
		StringBuilder str = new StringBuilder();			// 태그들을 읽어서 붙일 수 있다
		try {
			///String kmlFile = Environment.getExternalStorageDirectory()
			///		.getPath() + "/" + xmlFileName;
			///String kmlFile = getApplicationContext().getFileStreamPath("meal.xml");
			//InputStream is = new FileInputStream(getApplicationContext().getFileStreamPath(xmlFileName));
			InputStream is = getResources().openRawResource(R.raw.meal);


			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();		// 객체를 하나 만든다.

			Document document = docBuilder.parse(is);	// 의미있는 단위로 잘라준다.

			if (document == null) {
				Log.v("REALLY BAD!!!!", "document was NOT made by parser");
				return "BAD-ERROR";
			}
			// put all data into NodeLists
			NodeList listNameTag = document.getElementsByTagName("name");			// Tag 이름을 가진 값을 모두 가져온다.
			NodeList listCoordinatesTag = document
					.getElementsByTagName("price");
			NodeList listCourseTag = document.getElementsByTagName("calories");

			// traverse NodeLists for elements: <name>, <price>, <course>.
			str.append(getAllDataFromNodeList(listNameTag, "name"));
			str.append(getAllDataFromNodeList(listCoordinatesTag, "price"));
			str.append(getAllDataFromNodeList(listCourseTag, "calories"));

		} catch (FileNotFoundException e) {
			Log.e("W3C Error", e.getMessage());
		} catch (ParserConfigurationException e) {
			Log.e("W3C Error", e.getMessage());
		} catch (SAXException e) {
			Log.e("W3C Error", e.getMessage());
		} catch (IOException e) {
			Log.e("W3C Error", e.getMessage());
		}
		return str.toString();
	}// useW3cOrgDocumentBuilder

	private Object getAllDataFromNodeList(NodeList list, String strElementName) {	// NodeList의 값을 하나씩 가져온다
		StringBuilder str = new StringBuilder();
		// dealing with the <strElementName> tag
		str.append("\n\nNodeList for:  <" + strElementName + "> Tag");
		for (int i = 0; i < list.getLength(); i++) {

			Node node = list.item(i);
			int size = node.getAttributes().getLength();			// Node안의 Atribute의 길이를 가져온다.
			String text = node.getTextContent();					// 태그안의 내용을 가져온다.
			str.append("\n " + i + ": " + text);

			// get all attributes of the current element (i-th hole)
			for (int j = 0; j < size; j++) {
				String attrName = node.getAttributes().item(j).getNodeName();
				String attrValue = node.getAttributes().item(j).getNodeValue();

				str.append("\n attr. info-" + i + "-" + j + ": " + attrName
						+ " " + attrValue);
			}
		}

		return str;
	}//getAllDataFromNodeList


}// ActivityMain
