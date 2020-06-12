package com.speridian.benefits2.beans;

/**
 * 
 * <pre>
 * ViewObject for Document
 * </pre>
 *
 * @author jithin.kuriakose
 * @since 31-Mar-2017
 *
 */
public class DocumentVO {
	
	private String documentId;
	
	private String fieldName;
	
	private String documentType;
	private String recordId;
	private String recordLabel;
	private String recordName;
	
	
	private String docManUuid;
	private String uploadUrl;
	
	private String downloadUrl;
	
	private Boolean mandatory;
	

	public DocumentVO() {
		super();
	}

	public DocumentVO(String documentId, String fieldName, String documentType, String recordId,
			String recordLabel, String recordName) {
		super();
		this.documentId = documentId;
		this.fieldName = fieldName;
		this.documentType = documentType;
		this.recordId = recordId;
		this.recordLabel = recordLabel;
		this.recordName = recordName;
	}
	

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordLabel() {
		return recordLabel;
	}

	public void setRecordLabel(String recordLabel) {
		this.recordLabel = recordLabel;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getDocManUuid() {
		return docManUuid;
	}

	public void setDocManUuid(String docManUuid) {
		this.docManUuid = docManUuid;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
}
