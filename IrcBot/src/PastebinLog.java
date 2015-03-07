import java.io.BufferedReader;
import java.io.FileReader;

import com.besaba.revonline.pastebinapi.Pastebin;
import com.besaba.revonline.pastebinapi.impl.factory.PastebinFactory;
import com.besaba.revonline.pastebinapi.paste.Paste;
import com.besaba.revonline.pastebinapi.paste.PasteBuilder;
import com.besaba.revonline.pastebinapi.paste.PasteExpire;
import com.besaba.revonline.pastebinapi.paste.PasteVisiblity;
import com.besaba.revonline.pastebinapi.response.Response;

public class PastebinLog {
  public static final String DEV_KEY = "f283c56d32af921d16de8c1d0bea7262";

  public static String send() {
    PastebinFactory factory = new PastebinFactory();
    Pastebin pastebin = factory.createPastebin(DEV_KEY);
    // get a pastebuilder to build the paste I want to publish
    final PasteBuilder pasteBuilder = factory.createPaste();

    // Title paste
    pasteBuilder.setTitle("#kukko log");
    // What will be inside the paste?
    pasteBuilder.setRaw(readLogFile());
    // Which syntax will use the paste?
    pasteBuilder.setMachineFriendlyLanguage("xml");
    // What is the visibility of this paste?
    pasteBuilder.setVisiblity(PasteVisiblity.Public);
    // When the paste will expire?
    pasteBuilder.setExpire(PasteExpire.TenMinutes);

    // when i'm ready, create the Paste object
    final Paste paste = pasteBuilder.build();

    // ask to Pastebin to post the paste
    // the .post method: if the paste has been published will return the key assigned
    // by pastebin
    final Response<String> postResult = pastebin.post(paste);

    if (postResult.hasError()) {
      System.out.println("Something wrong: " + postResult.getError());
      return "Something wrong: " + postResult.getError();
    }

    System.out.println("Paste published! Url: " + postResult.get());
    return "Log published! Url: " + postResult.get();
  }
  
  static String readLogFile(){
	  String everything = "";
	  try{
	  BufferedReader br = new BufferedReader(new FileReader("log.txt"));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();
	        }
	        everything = sb.toString();
	    } finally {
	        br.close();
	    }
	  } catch(Exception e){
		  //something went terribly wrong
	  }
		return everything;
  }
}