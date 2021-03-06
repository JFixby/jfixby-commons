
package com.jfixby.scarabei.aws.android.sqs;

import com.jfixby.scarabei.aws.api.sqs.SQSSendMessageParams;

public class AndroidSQSSendMessageParams implements SQSSendMessageParams {

	private String queuURL;
	private String body;

	@Override
	public void setQueueURL (final String queuURL) {
		this.queuURL = queuURL;
	}

	@Override
	public String getQueueURL () {
		return this.queuURL;
	}

	@Override
	public String getBody () {
		return this.body;
	}

	@Override
	public void setBody (final String body) {
		this.body = body;
	}

}
