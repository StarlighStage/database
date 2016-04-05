package url;

import static org.junit.Assert.*;

import org.junit.Test;


public class Text_Code_t_Analiser_changeTest {


	@Test
	public void testgetFinArug() {
		Code_t_Analiser_change snorm = new Code_t_Analiser_change("chara_id=107, name='持田亜里沙')");
		Code_t_Analiser_change snorm1 = new Code_t_Analiser_change("valist = [,], name='持田亜里沙')");
		Code_t_Analiser_change snorm2 = new Code_t_Analiser_change("valist = (,), name='持田亜里沙')");
		Code_t_Analiser_change snorm3 = new Code_t_Analiser_change("valist = [(,),(,)], name='持田亜里沙')");
		Code_t_Analiser_change snorm4 = new Code_t_Analiser_change("valist = [(,),(,)])");
		Code_t_Analiser_change serr = new Code_t_Analiser_change("valist = [(,),(,)]");
		assertEquals(snorm.getFinArug(), 12);
		assertEquals(snorm1.getFinArug(), 12);
		assertEquals(snorm2.getFinArug(), 12);
		assertEquals(snorm3.getFinArug(), 18);
		assertEquals(snorm4.getFinArug(), 18);
		assertEquals(serr.getFinArug(), -1);
		assertEquals(serr.errno, 100);
	}
	
	@Test
	public void testAnalyse(){
		Code_t_Analiser_change snorm = new Code_t_Analiser_change("chara_id=107,valist = [(,),(,)], name='持田亜里沙')");
		snorm.log=true;
		snorm.elev=3;
		System.out.println("name='持田亜里沙'".indexOf('='));
		assertTrue(snorm.hasNext());
		assertTrue(snorm.analyse());
		assertEquals(snorm.getName(),"chara_id");
		assertEquals(snorm.getValue(),"107");
		assertTrue(snorm.hasNext());
		assertTrue(snorm.analyse());
		assertEquals(snorm.getName(),"valist");
		assertEquals(snorm.getValue(),"[(,),(,)]");
		assertTrue(snorm.hasNext());
		assertTrue(snorm.analyse());
		System.out.println(snorm.errorMessage);
		assertEquals(snorm.getName(),"name");
		assertEquals(snorm.getValue(),"持田亜里沙");
		assertFalse(snorm.hasNext());
	}
}
