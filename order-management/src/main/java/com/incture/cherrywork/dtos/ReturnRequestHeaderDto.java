package com.incture.cherrywork.dtos;

import java.util.Date;
import java.util.List;

import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
public @Data class ReturnRequestHeaderDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String returnReqNum;

	private String workflowInstance;

	private String requestedBy;

	private Date requestDate;

	private String requestCategory;

	private String requestType;

	private String requestStatusCode;

	private String referenceDocType;

	private String refDocNum;

	private String requestShortText;

	private String orderReason;

	private ExchangeHeaderDto exchangeHeaderDto;

	private List<ReturnItemDto> returnItemList;

	public ReturnRequestHeaderDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReturnRequestHeaderDto(String returnReqNum, String workflowInstance, String requestedBy, Date requestDate,
			String requestCategory, String requestType, String requestStatusCode, String referenceDocType,
			String refDocNum, String requestShortText, String orderReason, ExchangeHeaderDto exchangeHeaderDto,
			List<ReturnItemDto> returnItemList, List<CommentDto> commentDtoList) {
		super();
		this.returnReqNum = returnReqNum;
		this.workflowInstance = workflowInstance;
		this.requestedBy = requestedBy;
		this.requestDate = requestDate;
		this.requestCategory = requestCategory;
		this.requestType = requestType;
		this.requestStatusCode = requestStatusCode;
		this.referenceDocType = referenceDocType;
		this.refDocNum = refDocNum;
		this.requestShortText = requestShortText;
		this.orderReason = orderReason;
		this.exchangeHeaderDto = exchangeHeaderDto;
		this.returnItemList = returnItemList;
		this.commentDtoList = commentDtoList;
	}

	private List<CommentDto> commentDtoList;

	public String getReturnReqNum() {
		return returnReqNum;
	}

	public void setReturnReqNum(String returnReqNum) {
		this.returnReqNum = returnReqNum;
	}

	public String getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(String workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestCategory() {
		return requestCategory;
	}

	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestStatusCode() {
		return requestStatusCode;
	}

	public void setRequestStatusCode(String requestStatusCode) {
		this.requestStatusCode = requestStatusCode;
	}

	public String getReferenceDocType() {
		return referenceDocType;
	}

	public void setReferenceDocType(String referenceDocType) {
		this.referenceDocType = referenceDocType;
	}

	public String getRefDocNum() {
		return refDocNum;
	}

	public void setRefDocNum(String refDocNum) {
		this.refDocNum = refDocNum;
	}

	public String getRequestShortText() {
		return requestShortText;
	}

	public void setRequestShortText(String requestShortText) {
		this.requestShortText = requestShortText;
	}

	public String getOrderReason() {
		return orderReason;
	}

	public void setOrderReason(String orderReason) {
		this.orderReason = orderReason;
	}

	public ExchangeHeaderDto getExchangeHeaderDto() {
		return exchangeHeaderDto;
	}

	public void setExchangeHeaderDto(ExchangeHeaderDto exchangeHeaderDto) {
		this.exchangeHeaderDto = exchangeHeaderDto;
	}

	public List<ReturnItemDto> getReturnItemList() {
		return returnItemList;
	}

	public void setReturnItemList(List<ReturnItemDto> returnItemList) {
		this.returnItemList = returnItemList;
	}

	public List<CommentDto> getCommentDtoList() {
		return commentDtoList;
	}

	public void setCommentDtoList(List<CommentDto> commentDtoList) {
		this.commentDtoList = commentDtoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Boolean getValidForUsage() {
		return null;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		throw new UnsupportedOperationException();
	}

}

