/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.cloudsmith.stackhammer.api.test;

import static junit.framework.Assert.fail;

import org.cloudsmith.stackhammer.api.test.AccessTokenRetriever.GitHubApplication;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 */
public class Activator implements BundleActivator {

	private static final String PARAM_GITHUB_CLIENT_ID = "github.client.id";

	private static final String PARAM_GITHUB_CLIENT_SECRET = "github.client.secret";

	public static Activator getInstance() {
		return instance;
	}

	private GitHubApplication gitHubApplication;

	private String authServiceURL;

	private BundleContext context;

	private static Activator instance;

	private String accessToken;

	public String getAccessToken() {
		if(accessToken == null) {
			AccessTokenRetriever retriever = new AccessTokenRetriever(
				getGitHubLogin(), getGitHubPassword(), gitHubApplication);
			try {
				accessToken = retriever.getAccessToken("repo", "user");
			}
			catch(Exception e) {
				fail(e.getMessage());
			}
		}
		return accessToken;
	}

	public String getAuthServiceURL() {
		return authServiceURL;
	}

	public GitHubApplication getGitHubApplication() {
		return gitHubApplication;
	}

	public String getGitHubLogin() {
		return getProperty("github.login", "cs-test1");
	}

	public String getGitHubPassword() {
		return getProperty("github.password", "tester123");
	}

	public String getProperty(String key, String defaultValue) {
		String value = context.getProperty(key);
		if(value == null)
			value = defaultValue;
		return value;
	}

	public String getRequiredProperty(String key) {
		String value = getProperty(key, null);
		if(value == null)
			throw new IllegalStateException("Missing required property: " + key);
		return value;
	}

	public String getSymbolicName() {
		return context.getBundle().getSymbolicName();
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		instance = this;
		gitHubApplication = new GitHubApplication(
			getRequiredProperty(PARAM_GITHUB_CLIENT_ID), getRequiredProperty(PARAM_GITHUB_CLIENT_SECRET));
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		instance = null;
	}
} // OAuthActivator
