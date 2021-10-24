/**
 * 
 */
package com.asatisamaj.matrimony.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.asatisamaj.matrimony.entities.MembersDetail;

/**
 * The Class AppUtil.
 *
 * @author pavan.solapure
 */
public class AppUtil {

	/**
	 * Checks if is collection empty.
	 *
	 * @param collection the collection
	 * @return true, if is collection empty
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is object empty.
	 *
	 * @param object the object
	 * @return true, if is object empty
	 */
	public static boolean isObjectEmpty(Object object) {
		if (object == null)
			return true;
		else if (object instanceof String) {
			if (((String) object).trim().length() == 0) {
				return true;
			}
		} else if (object instanceof Collection) {
			return isCollectionEmpty((Collection<?>) object);
		}
		return false;
	}

	/**
	 * Gets the bean to json string.
	 *
	 * @param beanClasses the bean classes
	 * @return the bean to json string
	 */
	public static String getBeanToJsonString(Object... beanClasses) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Object beanClass : beanClasses) {
			stringBuilder.append(getBeanToJsonString(beanClass));
			stringBuilder.append(", ");
		}
		return stringBuilder.toString();
	}

	/**
	 * Concatenate.
	 *
	 * @param listOfItems the list of items
	 * @param separator   the separator
	 * @return the string
	 */
	public String concatenate(List<String> listOfItems, String separator) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> stit = listOfItems.iterator();

		while (stit.hasNext()) {
			sb.append(stit.next());
			if (stit.hasNext()) {
				sb.append(separator);
			}
		}

		return sb.toString();
	}

	/**
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object maskResult(Object object) {
		List<MembersDetail> memberList;
		Page<MembersDetail> pageTuts = (Page<MembersDetail>) object;
		memberList = pageTuts.getContent();
		memberList.forEach(member -> {
			if (!AppUtil.isObjectEmpty(member.getMobile1()))
				member.setMobile1(member.getMobile1().substring(4).concat("XXXX"));
			if (!AppUtil.isObjectEmpty(member.getMobile2()))
				member.setMobile2(member.getMobile2().substring(4).concat("XXXX"));

		});

		pageTuts = new PageImpl<>(memberList, pageTuts.getPageable(), pageTuts.getTotalElements());

		return pageTuts;
	}
}
