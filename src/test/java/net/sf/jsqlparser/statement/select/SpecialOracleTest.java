/*-
 * #%L
 * JSQLParser library
 * %%
 * Copyright (C) 2004 - 2019 JSQLParser
 * %%
 * Dual licensed under GNU LGPL 2.1 or Apache License 2.0
 * #L%
 */
package net.sf.jsqlparser.statement.select;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import static net.sf.jsqlparser.test.TestUtils.assertSqlCanBeParsedAndDeparsed;
import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertTrue;
import org.junit.ComparisonFailure;
import org.junit.Test;

/**
 * Tries to parse and deparse all statments in net.sf.jsqlparser.test.oracle-tests.
 *
 * As a matter of fact there are a lot of files that can still not processed. Here a step by step
 * improvement is the way to go.
 *
 * The test ensures, that the successfull parsed file count does not decrease.
 *
 * @author toben
 */
public class SpecialOracleTest {

}
