package com.mopon.views;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;


public class Protobuf extends AbstractView {

	public Protobuf() {
		this.setContentType("application/octet-stream");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return false;
	}

	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		byte[] buffer = (byte[]) model.get("protoDate");
		ByteArrayOutputStream baos = this.createTemporaryOutputStream();
		baos.write(buffer);
		this.writeToResponse(response, baos);
	}

}
