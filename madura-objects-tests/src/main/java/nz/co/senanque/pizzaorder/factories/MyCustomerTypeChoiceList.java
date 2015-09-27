package nz.co.senanque.pizzaorder.factories;

import java.util.ArrayList;
import java.util.List;

import nz.co.senanque.validationengine.choicelists.ChoiceBase;
import nz.co.senanque.validationengine.choicelists.ChoiceListFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component("customerType")
public class MyCustomerTypeChoiceList implements ChoiceListFactory {

	private final Logger logger = LoggerFactory.getLogger(MyCustomerTypeChoiceList.class);
	public MyCustomerTypeChoiceList() {
		logger.debug("");
	}

	public List<ChoiceBase> getChoiceList(
			MessageSourceAccessor messageSourceAccessor) {
		List<ChoiceBase> ret = new ArrayList<ChoiceBase>();
		ret.add(new ChoiceBase("1","1",messageSourceAccessor));
		ret.add(new ChoiceBase("2","2",messageSourceAccessor));
		ret.add(new ChoiceBase("3","3",messageSourceAccessor));
		return ret;
	}

}