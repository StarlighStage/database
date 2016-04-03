package url;

import static org.junit.Assert.*;

import org.junit.Test;

public class HtmlAnaliserTest {


	@Test
	public void testCheckFlag() {
		WebsiteReader asd = new WebsiteReader();
		String ty = asd.pageReader("https://starlight.kirara.ca/card/200023/table");
		HtmlAnaliser analyser = new HtmlAnaliser(ty);
		assertTrue(analyser.checkFlag("!DOCTYPE html"));
		//System.out.println("["+analyser.getFlag()+"]");
		analyser.setMainScore("div");
		assertFalse(analyser.checkFlag("!DOCTYPE html"));
		System.out.println(analyser.getText());
		assertTrue(analyser.checkFlag("a href=\"https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=KK2MLB7FGES32\""));
	}

}
