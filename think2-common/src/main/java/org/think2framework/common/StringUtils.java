package org.think2framework.common;

import java.util.*;

public class StringUtils {
	/**
	 * The empty String <code>""</code>.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	/**
	 * Represents a failed index search.
	 * 
	 * @since 2.1
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * 对象转字符串,如果对象为null则返回空
	 * 
	 * @param object
	 *            对象
	 * @return 字符串
	 */
	public static String toString(Object object) {
		return null == object ? EMPTY : object.toString();
	}

	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * Checks if a String is not empty ("") and not null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a String is not empty (""), not null and not whitespace only.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null and not
	 *         whitespace
	 * @since 2.0
	 */
	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String,
	 * handling <code>null</code> by returning <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.trim(null)          = null
	 * StringUtils.trim("")            = ""
	 * StringUtils.trim("     ")       = ""
	 * StringUtils.trim("abc")         = "abc"
	 * StringUtils.trim("    abc    ") = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed string, <code>null</code> if null String input
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning <code>null</code> if the String is empty ("") after the trim or
	 * if it is <code>null</code>.
	 *
	 * <pre>
	 * StringUtils.trimToNull(null)          = null
	 * StringUtils.trimToNull("")            = null
	 * StringUtils.trimToNull("     ")       = null
	 * StringUtils.trimToNull("abc")         = "abc"
	 * StringUtils.trimToNull("    abc    ") = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, <code>null</code> if only chars &lt;= 32,
	 *         empty or null String input
	 * @since 2.0
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning an empty String ("") if the String is empty ("") after the trim
	 * or if it is <code>null</code>.
	 * 
	 * <pre>
	 * StringUtils.trimToEmpty(null)          = ""
	 * StringUtils.trimToEmpty("")            = ""
	 * StringUtils.trimToEmpty("     ")       = ""
	 * StringUtils.trimToEmpty("abc")         = "abc"
	 * StringUtils.trimToEmpty("    abc    ") = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 *
	 * @see String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal
	 * ignoring the case.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered equal. Comparison is case insensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @see String#equalsIgnoreCase(String)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case insensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> or empty ("") String will return
	 * <code>INDEX_NOT_FOUND (-1)</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *)         = -1
	 * StringUtils.indexOf("", *)           = -1
	 * StringUtils.indexOf("aabaabaa", 'a') = 0
	 * StringUtils.indexOf("aabaabaa", 'b') = 2
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return INDEX_NOT_FOUND;
		}
		return str.indexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the first index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#indexOf(int, int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> or empty ("") String will return
	 * <code>(INDEX_NOT_FOUND) -1</code>. A negative start position is treated
	 * as zero. A start position greater than the string length returns
	 * <code>-1</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf("", *, *)            = -1
	 * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
	 * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
	 * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
	 * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return INDEX_NOT_FOUND;
		}
		return str.indexOf(searchChar, startPos);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *)          = -1
	 * StringUtils.indexOf(*, null)          = -1
	 * StringUtils.indexOf("", "")           = 0
	 * StringUtils.indexOf("", *)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a")  = 0
	 * StringUtils.indexOf("aabaabaa", "b")  = 2
	 * StringUtils.indexOf("aabaabaa", "ab") = 1
	 * StringUtils.indexOf("aabaabaa", "")   = 0
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.indexOf(searchStr);
	}

	/**
	 * <p>
	 * Finds the n-th index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.ordinalIndexOf(null, *, *)          = -1
	 * StringUtils.ordinalIndexOf(*, null, *)          = -1
	 * StringUtils.ordinalIndexOf("", "", *)           = 0
	 * StringUtils.ordinalIndexOf("aabaabaa", "a", 1)  = 0
	 * StringUtils.ordinalIndexOf("aabaabaa", "a", 2)  = 1
	 * StringUtils.ordinalIndexOf("aabaabaa", "b", 1)  = 2
	 * StringUtils.ordinalIndexOf("aabaabaa", "b", 2)  = 5
	 * StringUtils.ordinalIndexOf("aabaabaa", "ab", 1) = 1
	 * StringUtils.ordinalIndexOf("aabaabaa", "ab", 2) = 4
	 * StringUtils.ordinalIndexOf("aabaabaa", "", 1)   = 0
	 * StringUtils.ordinalIndexOf("aabaabaa", "", 2)   = 0
	 * </pre>
	 *
	 * <p>
	 * Note that 'head(String str, int n)' may be implemented as:
	 * </p>
	 *
	 * <pre>
	 * str.substring(0, lastOrdinalIndexOf(str, "\n", n))
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param ordinal
	 *            the n-th <code>searchStr</code> to find
	 * @return the n-th index of the search String, <code>-1</code> (
	 *         <code>INDEX_NOT_FOUND</code>) if no match or <code>null</code>
	 *         string input
	 * @since 2.1
	 */
	public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
		return ordinalIndexOf(str, searchStr, ordinal, false);
	}

	/**
	 * <p>
	 * Finds the n-th index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param ordinal
	 *            the n-th <code>searchStr</code> to find
	 * @param lastIndex
	 *            true if lastOrdinalIndexOf() otherwise false if
	 *            ordinalIndexOf()
	 * @return the n-th index of the search String, <code>-1</code> (
	 *         <code>INDEX_NOT_FOUND</code>) if no match or <code>null</code>
	 *         string input
	 */
	// Shared code between ordinalIndexOf(String,String,int) and
	// lastOrdinalIndexOf(String,String,int)
	private static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex) {
		if (str == null || searchStr == null || ordinal <= 0) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return lastIndex ? str.length() : 0;
		}
		int found = 0;
		int index = lastIndex ? str.length() : INDEX_NOT_FOUND;
		do {
			if (lastIndex) {
				index = str.lastIndexOf(searchStr, index - 1);
			} else {
				index = str.indexOf(searchStr, index + 1);
			}
			if (index < 0) {
				return index;
			}
			found++;
		} while (found < ordinal);
		return index;
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(String, int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position is treated as zero. An empty ("") search String always matches.
	 * A start position greater than the string length only matches an empty
	 * search String.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf(*, null, *)          = -1
	 * StringUtils.indexOf("", "", 0)           = 0
	 * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
	 * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
	 * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
	 * StringUtils.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtils.indexOf("aabaabaa", "", 2)   = 2
	 * StringUtils.indexOf("abc", "", 9)        = 3
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		// JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
		if (searchStr.length() == 0 && startPos >= str.length()) {
			return str.length();
		}
		return str.indexOf(searchStr, startPos);
	}

	/**
	 * <p>
	 * Case in-sensitive find of the first index within a String.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position is treated as zero. An empty ("") search String always matches.
	 * A start position greater than the string length only matches an empty
	 * search String.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOfIgnoreCase(null, *)          = -1
	 * StringUtils.indexOfIgnoreCase(*, null)          = -1
	 * StringUtils.indexOfIgnoreCase("", "")           = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "a")  = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "b")  = 2
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "ab") = 1
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.5
	 */
	public static int indexOfIgnoreCase(String str, String searchStr) {
		return indexOfIgnoreCase(str, searchStr, 0);
	}

	/**
	 * <p>
	 * Case in-sensitive find of the first index within a String from the
	 * specified position.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position is treated as zero. An empty ("") search String always matches.
	 * A start position greater than the string length only matches an empty
	 * search String.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
	 * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
	 * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
	 * StringUtils.indexOfIgnoreCase("abc", "", 9)        = 3
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.5
	 */
	public static int indexOfIgnoreCase(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		if (startPos < 0) {
			startPos = 0;
		}
		int endLimit = (str.length() - searchStr.length()) + 1;
		if (startPos > endLimit) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return startPos;
		}
		for (int i = startPos; i < endLimit; i++) {
			if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
				return i;
			}
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)         = -1
	 * StringUtils.lastIndexOf("", *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
	 * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return INDEX_NOT_FOUND;
		}
		return str.lastIndexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the last index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#lastIndexOf(int, int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position returns <code>-1</code>. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf("", *,  *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return INDEX_NOT_FOUND;
		}
		return str.lastIndexOf(searchChar, startPos);
	}

	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)          = -1
	 * StringUtils.lastIndexOf(*, null)          = -1
	 * StringUtils.lastIndexOf("", "")           = 0
	 * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
	 * StringUtils.lastIndexOf("aabaabaa", "")   = 8
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the last index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.lastIndexOf(searchStr);
	}

	/**
	 * <p>
	 * Finds the n-th last index within a String, handling <code>null</code>.
	 * This method uses {@link String#lastIndexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastOrdinalIndexOf(null, *, *)          = -1
	 * StringUtils.lastOrdinalIndexOf(*, null, *)          = -1
	 * StringUtils.lastOrdinalIndexOf("", "", *)           = 0
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 1)  = 7
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "a", 2)  = 6
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 1)  = 5
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "b", 2)  = 2
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 1) = 4
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2) = 1
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 1)   = 8
	 * StringUtils.lastOrdinalIndexOf("aabaabaa", "", 2)   = 8
	 * </pre>
	 *
	 * <p>
	 * Note that 'tail(String str, int n)' may be implemented as:
	 * </p>
	 *
	 * <pre>
	 * str.substring(lastOrdinalIndexOf(str, "\n", n) + 1)
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param ordinal
	 *            the n-th last <code>searchStr</code> to find
	 * @return the n-th last index of the search String, <code>-1</code> (
	 *         <code>INDEX_NOT_FOUND</code>) if no match or <code>null</code>
	 *         string input
	 * @since 2.5
	 */
	public static int lastOrdinalIndexOf(String str, String searchStr, int ordinal) {
		return ordinalIndexOf(str, searchStr, ordinal, true);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String, int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position returns <code>-1</code>. An empty ("") search String always
	 * matches unless the start position is negative. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf(*, null, *)          = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
	 * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.lastIndexOf(searchStr, startPos);
	}

	/**
	 * <p>
	 * Case in-sensitive find of the last index within a String.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position returns <code>-1</code>. An empty ("") search String always
	 * matches unless the start position is negative. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOfIgnoreCase(null, *)          = -1
	 * StringUtils.lastIndexOfIgnoreCase(*, null)          = -1
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.5
	 */
	public static int lastIndexOfIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return lastIndexOfIgnoreCase(str, searchStr, str.length());
	}

	/**
	 * <p>
	 * Case in-sensitive find of the last index within a String from the
	 * specified position.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position returns <code>-1</code>. An empty ("") search String always
	 * matches unless the start position is negative. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
	 * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.5
	 */
	public static int lastIndexOfIgnoreCase(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		if (startPos > (str.length() - searchStr.length())) {
			startPos = str.length() - searchStr.length();
		}
		if (startPos < 0) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return startPos;
		}

		for (int i = startPos; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
				return i;
			}
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * <p>
	 * Checks if String contains a search character, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>false</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *)    = false
	 * StringUtils.contains("", *)      = false
	 * StringUtils.contains("abc", 'a') = true
	 * StringUtils.contains("abc", 'z') = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return true if the String contains the search character, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, char searchChar) {
		if (isEmpty(str)) {
			return false;
		}
		return str.indexOf(searchChar) >= 0;
	}

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>.
	 * This method uses {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	/**
	 * <p>
	 * Checks if String contains a search String irrespective of case, handling
	 * <code>null</code>. Case-insensitivity is defined as by
	 * {@link String#equalsIgnoreCase(String)}.
	 *
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *) = false
	 * StringUtils.contains(*, null) = false
	 * StringUtils.contains("", "") = true
	 * StringUtils.contains("abc", "") = true
	 * StringUtils.contains("abc", "a") = true
	 * StringUtils.contains("abc", "z") = false
	 * StringUtils.contains("abc", "A") = true
	 * StringUtils.contains("abc", "Z") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String irrespective of
	 *         case or false if not or <code>null</code> string input
	 */
	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		int len = searchStr.length();
		int max = str.length() - len;
		for (int i = 0; i <= max; i++) {
			if (str.regionMatches(true, i, searchStr, 0, len)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Find the first index of any of a set of potential substrings.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> or zero length search array will return <code>-1</code>
	 * . A <code>null</code> search array entry will be ignored, but a search
	 * array containing "" will return <code>0</code> if <code>str</code> is not
	 * null. This method uses {@link String#indexOf(String)}.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOfAny(null, *)                     = -1
	 * StringUtils.indexOfAny(*, null)                     = -1
	 * StringUtils.indexOfAny(*, [])                       = -1
	 * StringUtils.indexOfAny("zzabyycdxx", ["ab","cd"])   = 2
	 * StringUtils.indexOfAny("zzabyycdxx", ["cd","ab"])   = 2
	 * StringUtils.indexOfAny("zzabyycdxx", ["mn","op"])   = -1
	 * StringUtils.indexOfAny("zzabyycdxx", ["zab","aby"]) = 1
	 * StringUtils.indexOfAny("zzabyycdxx", [""])          = 0
	 * StringUtils.indexOfAny("", [""])                    = 0
	 * StringUtils.indexOfAny("", ["a"])                   = -1
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStrs
	 *            the Strings to search for, may be null
	 * @return the first index of any of the searchStrs in str, -1 if no match
	 */
	public static int indexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return INDEX_NOT_FOUND;
		}
		int sz = searchStrs.length;

		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;

		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.indexOf(search);
			if (tmp == INDEX_NOT_FOUND) {
				continue;
			}

			if (tmp < ret) {
				ret = tmp;
			}
		}

		return (ret == Integer.MAX_VALUE) ? INDEX_NOT_FOUND : ret;
	}

	/**
	 * <p>
	 * Find the latest index of any of a set of potential substrings.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> search array will return <code>-1</code>. A
	 * <code>null</code> or zero length search array entry will be ignored, but
	 * a search array containing "" will return the length of <code>str</code>
	 * if <code>str</code> is not null. This method uses
	 * {@link String#indexOf(String)}
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOfAny(null, *)                   = -1
	 * StringUtils.lastIndexOfAny(*, null)                   = -1
	 * StringUtils.lastIndexOfAny(*, [])                     = -1
	 * StringUtils.lastIndexOfAny(*, [null])                 = -1
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["ab","cd"]) = 6
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["cd","ab"]) = 6
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn","op"]) = -1
	 * StringUtils.lastIndexOfAny("zzabyycdxx", ["mn",""])   = 10
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param searchStrs
	 *            the Strings to search for, may be null
	 * @return the last index of any of the Strings, -1 if no match
	 */
	public static int lastIndexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return INDEX_NOT_FOUND;
		}
		int sz = searchStrs.length;
		int ret = INDEX_NOT_FOUND;
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.lastIndexOf(search);
			if (tmp > ret) {
				ret = tmp;
			}
		}
		return ret;
	}

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 *
	 * <p>
	 * A negative start position can be used to start <code>n</code> characters
	 * from the end of the String.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> String will return <code>null</code>. An empty ("")
	 * String will return "".
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substring(null, *)   = null
	 * StringUtils.substring("", *)     = ""
	 * StringUtils.substring("abc", 0)  = "abc"
	 * StringUtils.substring("abc", 2)  = "c"
	 * StringUtils.substring("abc", 4)  = ""
	 * StringUtils.substring("abc", -2) = "bc"
	 * StringUtils.substring("abc", -4) = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to get the substring from, may be null
	 * @param start
	 *            the position to start from, negative means count back from the
	 *            end of the String by this many characters
	 * @return substring from start position, <code>null</code> if null String
	 *         input
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}
		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}
		return str.substring(start);
	}

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 *
	 * <p>
	 * A negative start position can be used to start/end <code>n</code>
	 * characters from the end of the String.
	 * </p>
	 *
	 * <p>
	 * The returned substring starts with the character in the
	 * <code>start</code> position and ends before the <code>end</code>
	 * position. All position counting is zero-based -- i.e., to start at the
	 * beginning of the string use <code>start = 0</code>. Negative start and
	 * end positions can be used to specify offsets relative to the end of the
	 * String.
	 * </p>
	 *
	 * <p>
	 * If <code>start</code> is not strictly to the left of <code>end</code>, ""
	 * is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substring(null, *, *)    = null
	 * StringUtils.substring("", * ,  *)    = "";
	 * StringUtils.substring("abc", 0, 2)   = "ab"
	 * StringUtils.substring("abc", 2, 0)   = ""
	 * StringUtils.substring("abc", 2, 4)   = "c"
	 * StringUtils.substring("abc", 4, 6)   = ""
	 * StringUtils.substring("abc", 2, 2)   = ""
	 * StringUtils.substring("abc", -2, -1) = "b"
	 * StringUtils.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 *
	 * @param str
	 *            the String to get the substring from, may be null
	 * @param start
	 *            the position to start from, negative means count back from the
	 *            end of the String by this many characters
	 * @param end
	 *            the position to end at (exclusive), negative means count back
	 *            from the end of the String by this many characters
	 * @return substring from start position to end positon, <code>null</code>
	 *         if null String input
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}
		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}
		// check length next
		if (end > str.length()) {
			end = str.length();
		}
		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}
		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}
		return str.substring(start, end);
	}

	/**
	 * <p>
	 * Gets the leftmost <code>len</code> characters of a String.
	 * </p>
	 *
	 * <p>
	 * If <code>len</code> characters are not available, or the String is
	 * <code>null</code>, the String will be returned without an exception. An
	 * empty String is returned if len is negative.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.left(null, *)    = null
	 * StringUtils.left(*, -ve)     = ""
	 * StringUtils.left("", *)      = ""
	 * StringUtils.left("abc", 0)   = ""
	 * StringUtils.left("abc", 2)   = "ab"
	 * StringUtils.left("abc", 4)   = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to get the leftmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the leftmost characters, <code>null</code> if null String input
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	/**
	 * <p>
	 * Gets the rightmost <code>len</code> characters of a String.
	 * </p>
	 *
	 * <p>
	 * If <code>len</code> characters are not available, or the String is
	 * <code>null</code>, the String will be returned without an an exception.
	 * An empty String is returned if len is negative.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.right(null, *)    = null
	 * StringUtils.right(*, -ve)     = ""
	 * StringUtils.right("", *)      = ""
	 * StringUtils.right("abc", 0)   = ""
	 * StringUtils.right("abc", 2)   = "bc"
	 * StringUtils.right("abc", 4)   = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to get the rightmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the rightmost characters, <code>null</code> if null String input
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	/**
	 * <p>
	 * Gets <code>len</code> characters from the middle of a String.
	 * </p>
	 *
	 * <p>
	 * If <code>len</code> characters are not available, the remainder of the
	 * String will be returned without an exception. If the String is
	 * <code>null</code>, <code>null</code> will be returned. An empty String is
	 * returned if len is negative or exceeds the length of <code>str</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.mid(null, *, *)    = null
	 * StringUtils.mid(*, *, -ve)     = ""
	 * StringUtils.mid("", 0, *)      = ""
	 * StringUtils.mid("abc", 0, 2)   = "ab"
	 * StringUtils.mid("abc", 0, 4)   = "abc"
	 * StringUtils.mid("abc", 2, 4)   = "c"
	 * StringUtils.mid("abc", 4, 2)   = ""
	 * StringUtils.mid("abc", -2, 2)  = "ab"
	 * </pre>
	 *
	 * @param str
	 *            the String to get the characters from, may be null
	 * @param pos
	 *            the position to start from, negative treated as zero
	 * @param len
	 *            the length of the required String
	 * @return the middle characters, <code>null</code> if null String input
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	/**
	 * <p>
	 * Gets the substring before the first occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. A <code>null</code>
	 * separator will return the input string.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the string input is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substringBefore(null, *)      = null
	 * StringUtils.substringBefore("", *)        = ""
	 * StringUtils.substringBefore("abc", "a")   = ""
	 * StringUtils.substringBefore("abcba", "b") = "a"
	 * StringUtils.substringBefore("abc", "c")   = "ab"
	 * StringUtils.substringBefore("abc", "d")   = "abc"
	 * StringUtils.substringBefore("abc", "")    = ""
	 * StringUtils.substringBefore("abc", null)  = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring before the first occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring after the first occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. A <code>null</code>
	 * separator will return the empty string if the input string is not
	 * <code>null</code>.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substringAfter(null, *)      = null
	 * StringUtils.substringAfter("", *)        = ""
	 * StringUtils.substringAfter(*, null)      = ""
	 * StringUtils.substringAfter("abc", "a")   = "bc"
	 * StringUtils.substringAfter("abcba", "b") = "cba"
	 * StringUtils.substringAfter("abc", "c")   = ""
	 * StringUtils.substringAfter("abc", "d")   = ""
	 * StringUtils.substringAfter("abc", "")    = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring after the first occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * <p>
	 * Gets the substring before the last occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. An empty or
	 * <code>null</code> separator will return the input string.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the string input is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substringBeforeLast(null, *)      = null
	 * StringUtils.substringBeforeLast("", *)        = ""
	 * StringUtils.substringBeforeLast("abcba", "b") = "abc"
	 * StringUtils.substringBeforeLast("abc", "c")   = "ab"
	 * StringUtils.substringBeforeLast("a", "a")     = ""
	 * StringUtils.substringBeforeLast("a", "z")     = "a"
	 * StringUtils.substringBeforeLast("a", null)    = "a"
	 * StringUtils.substringBeforeLast("a", "")      = "a"
	 * </pre>
	 *
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring before the last occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringBeforeLast(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == INDEX_NOT_FOUND) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring after the last occurrence of a separator. The
	 * separator is not returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. An empty or
	 * <code>null</code> separator will return the empty string if the input
	 * string is not <code>null</code>.
	 * </p>
	 *
	 * <p>
	 * If nothing is found, the empty string is returned.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substringAfterLast(null, *)      = null
	 * StringUtils.substringAfterLast("", *)        = ""
	 * StringUtils.substringAfterLast(*, "")        = ""
	 * StringUtils.substringAfterLast(*, null)      = ""
	 * StringUtils.substringAfterLast("abc", "a")   = "bc"
	 * StringUtils.substringAfterLast("abcba", "b") = "a"
	 * StringUtils.substringAfterLast("abc", "c")   = ""
	 * StringUtils.substringAfterLast("a", "a")     = ""
	 * StringUtils.substringAfterLast("a", "z")     = ""
	 * </pre>
	 *
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring after the last occurrence of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == INDEX_NOT_FOUND || pos == (str.length() - separator.length())) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two instances of the same
	 * String.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> tag returns <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substringBetween(null, *)            = null
	 * StringUtils.substringBetween("", "")             = ""
	 * StringUtils.substringBetween("", "tag")          = null
	 * StringUtils.substringBetween("tagabctag", null)  = null
	 * StringUtils.substringBetween("tagabctag", "")    = ""
	 * StringUtils.substringBetween("tagabctag", "tag") = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String containing the substring, may be null
	 * @param tag
	 *            the String before and after the substring, may be null
	 * @return the substring, <code>null</code> if no match
	 * @since 2.0
	 */
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two Strings. Only the first
	 * match is returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> open/close returns <code>null</code> (no match). An
	 * empty ("") open and close returns an empty string.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.substringBetween("wx[b]yz", "[", "]") = "b"
	 * StringUtils.substringBetween(null, *, *)          = null
	 * StringUtils.substringBetween(*, null, *)          = null
	 * StringUtils.substringBetween(*, *, null)          = null
	 * StringUtils.substringBetween("", "", "")          = ""
	 * StringUtils.substringBetween("", "", "]")         = null
	 * StringUtils.substringBetween("", "[", "]")        = null
	 * StringUtils.substringBetween("yabcz", "", "")     = ""
	 * StringUtils.substringBetween("yabcz", "y", "z")   = "abc"
	 * StringUtils.substringBetween("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String containing the substring, may be null
	 * @param open
	 *            the String before the substring, may be null
	 * @param close
	 *            the String after the substring, may be null
	 * @return the substring, <code>null</code> if no match
	 * @since 2.0
	 */
	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != INDEX_NOT_FOUND) {
			int end = str.indexOf(close, start + open.length());
			if (end != INDEX_NOT_FOUND) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two instances of the same
	 * String.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> tag returns <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.getNestedString(null, *)            = null
	 * StringUtils.getNestedString("", "")             = ""
	 * StringUtils.getNestedString("", "tag")          = null
	 * StringUtils.getNestedString("tagabctag", null)  = null
	 * StringUtils.getNestedString("tagabctag", "")    = ""
	 * StringUtils.getNestedString("tagabctag", "tag") = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String containing nested-string, may be null
	 * @param tag
	 *            the String before and after nested-string, may be null
	 * @return the nested String, <code>null</code> if no match
	 * @deprecated Use the better named
	 *             {@link #substringBetween(String, String)}. Method will be
	 *             removed in Commons Lang 3.0.
	 */
	public static String getNestedString(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two Strings. Only the first
	 * match is returned.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> open/close returns <code>null</code> (no match). An
	 * empty ("") open/close returns an empty string.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.getNestedString(null, *, *)          = null
	 * StringUtils.getNestedString("", "", "")          = ""
	 * StringUtils.getNestedString("", "", "tag")       = null
	 * StringUtils.getNestedString("", "tag", "tag")    = null
	 * StringUtils.getNestedString("yabcz", null, null) = null
	 * StringUtils.getNestedString("yabcz", "", "")     = ""
	 * StringUtils.getNestedString("yabcz", "y", "z")   = "abc"
	 * StringUtils.getNestedString("yabczyabcz", "y", "z")   = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String containing nested-string, may be null
	 * @param open
	 *            the String before nested-string, may be null
	 * @param close
	 *            the String after nested-string, may be null
	 * @return the nested String, <code>null</code> if no match
	 * @deprecated Use the better named
	 *             {@link #substringBetween(String, String, String)}. Method
	 *             will be removed in Commons Lang 3.0.
	 */
	public static String getNestedString(String str, String open, String close) {
		return substringBetween(str, open, close);
	}

	/**
	 * Gets a String's length or <code>0</code> if the String is
	 * <code>null</code>.
	 *
	 * @param str
	 *            a String or <code>null</code>
	 * @return String length or <code>0</code> if the String is
	 *         <code>null</code>.
	 * @since 2.4
	 */
	public static int length(String str) {
		return str == null ? 0 : str.length();
	}

	/**
	 * <p>
	 * Converts a String to upper case as per {@link String#toUpperCase()}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.upperCase(null)  = null
	 * StringUtils.upperCase("")    = ""
	 * StringUtils.upperCase("aBc") = "ABC"
	 * </pre>
	 *
	 * <p>
	 * <strong>Note:</strong> As described in the documentation for
	 * {@link String#toUpperCase()}, the result of this method is affected by
	 * the current locale. For platform-independent case transformations, the
	 * method {@link #lowerCase(String, Locale)} should be used with a specific
	 * locale (e.g. {@link Locale#ENGLISH}).
	 * </p>
	 *
	 * @param str
	 *            the String to upper case, may be null
	 * @return the upper cased String, <code>null</code> if null String input
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * <p>
	 * Converts a String to upper case as per {@link String#toUpperCase(Locale)}
	 * .
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.upperCase(null, Locale.ENGLISH)  = null
	 * StringUtils.upperCase("", Locale.ENGLISH)    = ""
	 * StringUtils.upperCase("aBc", Locale.ENGLISH) = "ABC"
	 * </pre>
	 *
	 * @param str
	 *            the String to upper case, may be null
	 * @param locale
	 *            the locale that defines the case transformation rules, must
	 *            not be null
	 * @return the upper cased String, <code>null</code> if null String input
	 * @since 2.5
	 */
	public static String upperCase(String str, Locale locale) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase(locale);
	}

	/**
	 * <p>
	 * Converts a String to lower case as per {@link String#toLowerCase()}.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lowerCase(null)  = null
	 * StringUtils.lowerCase("")    = ""
	 * StringUtils.lowerCase("aBc") = "abc"
	 * </pre>
	 *
	 * <p>
	 * <strong>Note:</strong> As described in the documentation for
	 * {@link String#toLowerCase()}, the result of this method is affected by
	 * the current locale. For platform-independent case transformations, the
	 * method {@link #lowerCase(String, Locale)} should be used with a specific
	 * locale (e.g. {@link Locale#ENGLISH}).
	 * </p>
	 *
	 * @param str
	 *            the String to lower case, may be null
	 * @return the lower cased String, <code>null</code> if null String input
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	/**
	 * <p>
	 * Converts a String to lower case as per {@link String#toLowerCase(Locale)}
	 * .
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lowerCase(null, Locale.ENGLISH)  = null
	 * StringUtils.lowerCase("", Locale.ENGLISH)    = ""
	 * StringUtils.lowerCase("aBc", Locale.ENGLISH) = "abc"
	 * </pre>
	 *
	 * @param str
	 *            the String to lower case, may be null
	 * @param locale
	 *            the locale that defines the case transformation rules, must
	 *            not be null
	 * @return the lower cased String, <code>null</code> if null String input
	 * @since 2.5
	 */
	public static String lowerCase(String str, Locale locale) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase(locale);
	}

	// Count matches
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Counts how many times the substring appears in the larger String.
	 * </p>
	 *
	 * <p>
	 * A <code>null</code> or empty ("") String input returns <code>0</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.countMatches(null, *)       = 0
	 * StringUtils.countMatches("", *)         = 0
	 * StringUtils.countMatches("abba", null)  = 0
	 * StringUtils.countMatches("abba", "")    = 0
	 * StringUtils.countMatches("abba", "a")   = 2
	 * StringUtils.countMatches("abba", "ab")  = 1
	 * StringUtils.countMatches("abba", "xxx") = 0
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param sub
	 *            the substring to count, may be null
	 * @return the number of occurrences, 0 if either String is
	 *         <code>null</code>
	 */
	public static int countMatches(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != INDEX_NOT_FOUND) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	// Character Tests
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if the String contains only unicode letters.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlpha(null)   = false
	 * StringUtils.isAlpha("")     = true
	 * StringUtils.isAlpha("  ")   = false
	 * StringUtils.isAlpha("abc")  = true
	 * StringUtils.isAlpha("ab2c") = false
	 * StringUtils.isAlpha("ab-c") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters, and is non-null
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetter(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters and space (' ').
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code> An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlphaSpace(null)   = false
	 * StringUtils.isAlphaSpace("")     = true
	 * StringUtils.isAlphaSpace("  ")   = true
	 * StringUtils.isAlphaSpace("abc")  = true
	 * StringUtils.isAlphaSpace("ab c") = true
	 * StringUtils.isAlphaSpace("ab2c") = false
	 * StringUtils.isAlphaSpace("ab-c") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters and space, and is
	 *         non-null
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetter(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters or digits.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlphanumeric(null)   = false
	 * StringUtils.isAlphanumeric("")     = true
	 * StringUtils.isAlphanumeric("  ")   = false
	 * StringUtils.isAlphanumeric("abc")  = true
	 * StringUtils.isAlphanumeric("ab c") = false
	 * StringUtils.isAlphanumeric("ab2c") = true
	 * StringUtils.isAlphanumeric("ab-c") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters or digits, and is
	 *         non-null
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters, digits or space (
	 * <code>' '</code>).
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAlphanumeric(null)   = false
	 * StringUtils.isAlphanumeric("")     = true
	 * StringUtils.isAlphanumeric("  ")   = true
	 * StringUtils.isAlphanumeric("abc")  = true
	 * StringUtils.isAlphanumeric("ab c") = true
	 * StringUtils.isAlphanumeric("ab2c") = true
	 * StringUtils.isAlphanumeric("ab-c") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters, digits or space, and
	 *         is non-null
	 */
	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetterOrDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not
	 * a unicode digit and returns false.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = true
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits or space (
	 * <code>' '</code>). A decimal point is not a unicode digit and returns
	 * false.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = true
	 * StringUtils.isNumeric("  ")   = true
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("12 3") = true
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains digits or space, and is
	 *         non-null
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only whitespace.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>true</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isWhitespace(null)   = false
	 * StringUtils.isWhitespace("")     = true
	 * StringUtils.isWhitespace("  ")   = true
	 * StringUtils.isWhitespace("abc")  = false
	 * StringUtils.isWhitespace("ab2c") = false
	 * StringUtils.isWhitespace("ab-c") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains whitespace, and is non-null
	 * @since 2.0
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only lowercase characters.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>false</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAllLowerCase(null)   = false
	 * StringUtils.isAllLowerCase("")     = false
	 * StringUtils.isAllLowerCase("  ")   = false
	 * StringUtils.isAllLowerCase("abc")  = true
	 * StringUtils.isAllLowerCase("abC") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains lowercase characters, and is
	 *         non-null
	 * @since 2.5
	 */
	public static boolean isAllLowerCase(String str) {
		if (str == null || isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLowerCase(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only uppercase characters.
	 * </p>
	 *
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String
	 * (length()=0) will return <code>false</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isAllUpperCase(null)   = false
	 * StringUtils.isAllUpperCase("")     = false
	 * StringUtils.isAllUpperCase("  ")   = false
	 * StringUtils.isAllUpperCase("ABC")  = true
	 * StringUtils.isAllUpperCase("aBC") = false
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains uppercase characters, and is
	 *         non-null
	 * @since 2.5
	 */
	public static boolean isAllUpperCase(String str) {
		if (str == null || isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isUpperCase(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	// Defaults
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Returns either the passed in String, or if the String is
	 * <code>null</code>, an empty String ("").
	 * </p>
	 *
	 * <pre>
	 * StringUtils.defaultString(null)  = ""
	 * StringUtils.defaultString("")    = ""
	 * StringUtils.defaultString("bat") = "bat"
	 * </pre>
	 *
	 * @see String#valueOf(Object)
	 * @param str
	 *            the String to check, may be null
	 * @return the passed in String, or the empty String if it was
	 *         <code>null</code>
	 */
	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is
	 * <code>null</code>, the value of <code>defaultStr</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.defaultString(null, "NULL")  = "NULL"
	 * StringUtils.defaultString("", "NULL")    = ""
	 * StringUtils.defaultString("bat", "NULL") = "bat"
	 * </pre>
	 *
	 * @see String#valueOf(Object)
	 * @param str
	 *            the String to check, may be null
	 * @param defaultStr
	 *            the default String to return if the input is <code>null</code>
	 *            , may be null
	 * @return the passed in String, or the default if it was <code>null</code>
	 */
	public static String defaultString(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is whitespace,
	 * empty ("") or <code>null</code>, the value of <code>defaultStr</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
	 * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
	 * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
	 * StringUtils.defaultIfBlank("", null)      = null
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param defaultStr
	 *            the default String to return if the input is whitespace, empty
	 *            ("") or <code>null</code>, may be null
	 * @return the passed in String, or the default
	 * @see StringUtils#defaultString(String, String)
	 * @since 2.6
	 */
	public static String defaultIfBlank(String str, String defaultStr) {
		return StringUtils.isBlank(str) ? defaultStr : str;
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is empty or
	 * <code>null</code>, the value of <code>defaultStr</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
	 * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
	 * StringUtils.defaultIfEmpty("", null)      = null
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param defaultStr
	 *            the default String to return if the input is empty ("") or
	 *            <code>null</code>, may be null
	 * @return the passed in String, or the default
	 * @see StringUtils#defaultString(String, String)
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return StringUtils.isEmpty(str) ? defaultStr : str;
	}

	/**
	 * <p>
	 * Abbreviates a String using ellipses. This will turn
	 * "Now is the time for all good men" into "Now is the time for..."
	 * </p>
	 *
	 * <p>
	 * Specifically:
	 * <ul>
	 * <li>If <code>str</code> is less than <code>maxWidth</code> characters
	 * long, return it.</li>
	 * <li>Else abbreviate it to <code>(substring(str, 0, max-3) + "...")</code>
	 * .</li>
	 * <li>If <code>maxWidth</code> is less than <code>4</code>, throw an
	 * <code>IllegalArgumentException</code>.</li>
	 * <li>In no case will it return a String of length greater than
	 * <code>maxWidth</code>.</li>
	 * </ul>
	 * </p>
	 *
	 * <pre>
	 * StringUtils.abbreviate(null, *)      = null
	 * StringUtils.abbreviate("", 4)        = ""
	 * StringUtils.abbreviate("abcdefg", 6) = "abc..."
	 * StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
	 * StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
	 * StringUtils.abbreviate("abcdefg", 4) = "a..."
	 * StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param maxWidth
	 *            maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws IllegalArgumentException
	 *             if the width is too small
	 * @since 2.0
	 */
	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}

	/**
	 * <p>
	 * Abbreviates a String using ellipses. This will turn
	 * "Now is the time for all good men" into "...is the time for..."
	 * </p>
	 *
	 * <p>
	 * Works like <code>abbreviate(String, int)</code>, but allows you to
	 * specify a "left edge" offset. Note that this left edge is not necessarily
	 * going to be the leftmost character in the result, or the first character
	 * following the ellipses, but it will appear somewhere in the result.
	 *
	 * <p>
	 * In no case will it return a String of length greater than
	 * <code>maxWidth</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.abbreviate(null, *, *)                = null
	 * StringUtils.abbreviate("", 0, 4)                  = ""
	 * StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
	 * StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
	 * StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
	 * StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
	 * StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
	 * StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
	 * </pre>
	 *
	 * @param str
	 *            the String to check, may be null
	 * @param offset
	 *            left edge of source String
	 * @param maxWidth
	 *            maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws IllegalArgumentException
	 *             if the width is too small
	 * @since 2.0
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		if (maxWidth < 4) {
			throw new IllegalArgumentException("Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (offset > str.length()) {
			offset = str.length();
		}
		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}
		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
		}
		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}

	/**
	 * <p>
	 * Compares two Strings, and returns the portion where they differ. (More
	 * precisely, return the remainder of the second String, starting from where
	 * it's different from the first.)
	 * </p>
	 *
	 * <p>
	 * For example,
	 * <code>difference("i am a machine", "i am a robot") -> "robot"</code>.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.difference(null, null) = null
	 * StringUtils.difference("", "") = ""
	 * StringUtils.difference("", "abc") = "abc"
	 * StringUtils.difference("abc", "") = ""
	 * StringUtils.difference("abc", "abc") = ""
	 * StringUtils.difference("ab", "abxyz") = "xyz"
	 * StringUtils.difference("abcde", "abxyz") = "xyz"
	 * StringUtils.difference("abcde", "xyz") = "xyz"
	 * </pre>
	 *
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return the portion of str2 where it differs from str1; returns the empty
	 *         String if they are equal
	 * @since 2.0
	 */
	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == INDEX_NOT_FOUND) {
			return EMPTY;
		}
		return str2.substring(at);
	}

	/**
	 * <p>
	 * Compares two Strings, and returns the index at which the Strings begin to
	 * differ.
	 * </p>
	 *
	 * <p>
	 * For example,
	 * <code>indexOfDifference("i am a machine", "i am a robot") -> 7</code>
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOfDifference(null, null) = -1
	 * StringUtils.indexOfDifference("", "") = -1
	 * StringUtils.indexOfDifference("", "abc") = 0
	 * StringUtils.indexOfDifference("abc", "") = 0
	 * StringUtils.indexOfDifference("abc", "abc") = -1
	 * StringUtils.indexOfDifference("ab", "abxyz") = 2
	 * StringUtils.indexOfDifference("abcde", "abxyz") = 2
	 * StringUtils.indexOfDifference("abcde", "xyz") = 0
	 * </pre>
	 *
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return the index where str2 and str1 begin to differ; -1 if they are
	 *         equal
	 * @since 2.0
	 */
	public static int indexOfDifference(String str1, String str2) {
		if (str1 == str2) {
			return INDEX_NOT_FOUND;
		}
		if (str1 == null || str2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < str1.length() && i < str2.length(); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		if (i < str2.length() || i < str1.length()) {
			return i;
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * <p>
	 * Compares all Strings in an array and returns the index at which the
	 * Strings begin to differ.
	 * </p>
	 *
	 * <p>
	 * For example,
	 * <code>indexOfDifference(new String[] {"i am a machine", "i am a robot"}) -> 7</code>
	 * </p>
	 *
	 * <pre>
	 * StringUtils.indexOfDifference(null) = -1
	 * StringUtils.indexOfDifference(new String[] {}) = -1
	 * StringUtils.indexOfDifference(new String[] {"abc"}) = -1
	 * StringUtils.indexOfDifference(new String[] {null, null}) = -1
	 * StringUtils.indexOfDifference(new String[] {"", ""}) = -1
	 * StringUtils.indexOfDifference(new String[] {"", null}) = 0
	 * StringUtils.indexOfDifference(new String[] {"abc", null, null}) = 0
	 * StringUtils.indexOfDifference(new String[] {null, null, "abc"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"", "abc"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"abc", ""}) = 0
	 * StringUtils.indexOfDifference(new String[] {"abc", "abc"}) = -1
	 * StringUtils.indexOfDifference(new String[] {"abc", "a"}) = 1
	 * StringUtils.indexOfDifference(new String[] {"ab", "abxyz"}) = 2
	 * StringUtils.indexOfDifference(new String[] {"abcde", "abxyz"}) = 2
	 * StringUtils.indexOfDifference(new String[] {"abcde", "xyz"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"xyz", "abcde"}) = 0
	 * StringUtils.indexOfDifference(new String[] {"i am a machine", "i am a robot"}) = 7
	 * </pre>
	 *
	 * @param strs
	 *            array of strings, entries may be null
	 * @return the index where the strings begin to differ; -1 if they are all
	 *         equal
	 * @since 2.4
	 */
	public static int indexOfDifference(String[] strs) {
		if (strs == null || strs.length <= 1) {
			return INDEX_NOT_FOUND;
		}
		boolean anyStringNull = false;
		boolean allStringsNull = true;
		int arrayLen = strs.length;
		int shortestStrLen = Integer.MAX_VALUE;
		int longestStrLen = 0;

		// find the min and max string lengths; this avoids checking to make
		// sure we are not exceeding the length of the string each time through
		// the bottom loop.
		for (int i = 0; i < arrayLen; i++) {
			if (strs[i] == null) {
				anyStringNull = true;
				shortestStrLen = 0;
			} else {
				allStringsNull = false;
				shortestStrLen = Math.min(strs[i].length(), shortestStrLen);
				longestStrLen = Math.max(strs[i].length(), longestStrLen);
			}
		}

		// handle lists containing all nulls or all empty strings
		if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
			return INDEX_NOT_FOUND;
		}

		// handle lists containing some nulls or some empty strings
		if (shortestStrLen == 0) {
			return 0;
		}

		// find the position with the first difference across all strings
		int firstDiff = -1;
		for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
			char comparisonChar = strs[0].charAt(stringPos);
			for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
				if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
					firstDiff = stringPos;
					break;
				}
			}
			if (firstDiff != -1) {
				break;
			}
		}

		if (firstDiff == -1 && shortestStrLen != longestStrLen) {
			// we compared all of the characters up to the length of the
			// shortest string and didn't find a match, but the string lengths
			// vary, so return the length of the shortest string.
			return shortestStrLen;
		}
		return firstDiff;
	}

	/**
	 * <p>
	 * Compares all Strings in an array and returns the initial sequence of
	 * characters that is common to all of them.
	 * </p>
	 *
	 * <p>
	 * For example,
	 * <code>getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) -> "i am a "</code>
	 * </p>
	 *
	 * <pre>
	 * StringUtils.getCommonPrefix(null) = ""
	 * StringUtils.getCommonPrefix(new String[] {}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc"}) = "abc"
	 * StringUtils.getCommonPrefix(new String[] {null, null}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"", ""}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"", null}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc", null, null}) = ""
	 * StringUtils.getCommonPrefix(new String[] {null, null, "abc"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"", "abc"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc", ""}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"abc", "abc"}) = "abc"
	 * StringUtils.getCommonPrefix(new String[] {"abc", "a"}) = "a"
	 * StringUtils.getCommonPrefix(new String[] {"ab", "abxyz"}) = "ab"
	 * StringUtils.getCommonPrefix(new String[] {"abcde", "abxyz"}) = "ab"
	 * StringUtils.getCommonPrefix(new String[] {"abcde", "xyz"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"xyz", "abcde"}) = ""
	 * StringUtils.getCommonPrefix(new String[] {"i am a machine", "i am a robot"}) = "i am a "
	 * </pre>
	 *
	 * @param strs
	 *            array of String objects, entries may be null
	 * @return the initial sequence of characters that are common to all Strings
	 *         in the array; empty String if the array is null, the elements are
	 *         all null or if there is no common prefix.
	 * @since 2.4
	 */
	public static String getCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0) {
			return EMPTY;
		}
		int smallestIndexOfDiff = indexOfDifference(strs);
		if (smallestIndexOfDiff == INDEX_NOT_FOUND) {
			// all strings were identical
			if (strs[0] == null) {
				return EMPTY;
			}
			return strs[0];
		} else if (smallestIndexOfDiff == 0) {
			// there were no common initial characters
			return EMPTY;
		} else {
			// we found a common initial character sequence
			return strs[0].substring(0, smallestIndexOfDiff);
		}
	}

	/**
	 * <p>
	 * Check if a String starts with a specified prefix.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.startsWith(null, null)      = true
	 * StringUtils.startsWith(null, "abc")     = false
	 * StringUtils.startsWith("abcdef", null)  = false
	 * StringUtils.startsWith("abcdef", "abc") = true
	 * StringUtils.startsWith("ABCDEF", "abc") = false
	 * </pre>
	 *
	 * @see String#startsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param prefix
	 *            the prefix to find, may be null
	 * @return <code>true</code> if the String starts with the prefix, case
	 *         sensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean startsWith(String str, String prefix) {
		return startsWith(str, prefix, false);
	}

	/**
	 * <p>
	 * Case insensitive check if a String starts with a specified prefix.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case
	 * insensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.startsWithIgnoreCase(null, null)      = true
	 * StringUtils.startsWithIgnoreCase(null, "abc")     = false
	 * StringUtils.startsWithIgnoreCase("abcdef", null)  = false
	 * StringUtils.startsWithIgnoreCase("abcdef", "abc") = true
	 * StringUtils.startsWithIgnoreCase("ABCDEF", "abc") = true
	 * </pre>
	 *
	 * @see String#startsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param prefix
	 *            the prefix to find, may be null
	 * @return <code>true</code> if the String starts with the prefix, case
	 *         insensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}

	/**
	 * <p>
	 * Check if a String starts with a specified prefix (optionally case
	 * insensitive).
	 * </p>
	 *
	 * @see String#startsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param prefix
	 *            the prefix to find, may be null
	 * @param ignoreCase
	 *            inidicates whether the compare should ignore case (case
	 *            insensitive) or not.
	 * @return <code>true</code> if the String starts with the prefix or both
	 *         <code>null</code>
	 */
	private static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	/**
	 * <p>
	 * Check if a String ends with a specified suffix.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.endsWith(null, null)      = true
	 * StringUtils.endsWith(null, "def")     = false
	 * StringUtils.endsWith("abcdef", null)  = false
	 * StringUtils.endsWith("abcdef", "def") = true
	 * StringUtils.endsWith("ABCDEF", "def") = false
	 * StringUtils.endsWith("ABCDEF", "cde") = false
	 * </pre>
	 *
	 * @see String#endsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param suffix
	 *            the suffix to find, may be null
	 * @return <code>true</code> if the String ends with the suffix, case
	 *         sensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}

	/**
	 * <p>
	 * Case insensitive check if a String ends with a specified suffix.
	 * </p>
	 *
	 * <p>
	 * <code>null</code>s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case
	 * insensitive.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.endsWithIgnoreCase(null, null)      = true
	 * StringUtils.endsWithIgnoreCase(null, "def")     = false
	 * StringUtils.endsWithIgnoreCase("abcdef", null)  = false
	 * StringUtils.endsWithIgnoreCase("abcdef", "def") = true
	 * StringUtils.endsWithIgnoreCase("ABCDEF", "def") = true
	 * StringUtils.endsWithIgnoreCase("ABCDEF", "cde") = false
	 * </pre>
	 *
	 * @see String#endsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param suffix
	 *            the suffix to find, may be null
	 * @return <code>true</code> if the String ends with the suffix, case
	 *         insensitive, or both <code>null</code>
	 * @since 2.4
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return endsWith(str, suffix, true);
	}

	/**
	 * <p>
	 * Check if a String ends with a specified suffix (optionally case
	 * insensitive).
	 * </p>
	 *
	 * @see String#endsWith(String)
	 * @param str
	 *            the String to check, may be null
	 * @param suffix
	 *            the suffix to find, may be null
	 * @param ignoreCase
	 *            inidicates whether the compare should ignore case (case
	 *            insensitive) or not.
	 * @return <code>true</code> if the String starts with the prefix or both
	 *         <code>null</code>
	 */
	private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
		if (str == null || suffix == null) {
			return (str == null && suffix == null);
		}
		if (suffix.length() > str.length()) {
			return false;
		}
		int strOffset = str.length() - suffix.length();
		return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
	}

}
