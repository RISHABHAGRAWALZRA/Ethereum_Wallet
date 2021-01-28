package com.example.ethereum_wallet;

public class TransactionDetail {

    private String blockNumber;
    private String timeStamp;
    private String hash;
    private String nonce;
    private String blockHash;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gas;
    private String gasPrice;
    private String isError;
    private String txreceipt_status;
    private String input;
    private String contractAddress;
    private String cumulativeGasUsed;
    private String gasUsed;
    private String confirmations;


    // Getter Methods

    public String getBlockNumber() {
        return blockNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getHash() {
        return hash;
    }

    public String getNonce() {
        return nonce;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public String getTransactionIndex() {
        return transactionIndex;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getValue() {
        return value;
    }

    public String getGas() {
        return gas;
    }

    public String getGasPrice() {
        return gasPrice;
    }

    public String getIsError() {
        return isError;
    }

    public String getTxreceipt_status() {
        return txreceipt_status;
    }

    public String getInput() {
        return input;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public String getCumulativeGasUsed() {
        return cumulativeGasUsed;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public String getConfirmations() {
        return confirmations;
    }

    // Setter Methods

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public void setTransactionIndex(String transactionIndex) {
        this.transactionIndex = transactionIndex;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public void setIsError(String isError) {
        this.isError = isError;
    }

    public void setTxreceipt_status(String txreceipt_status) {
        this.txreceipt_status = txreceipt_status;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public void setCumulativeGasUsed(String cumulativeGasUsed) {
        this.cumulativeGasUsed = cumulativeGasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public void setConfirmations(String confirmations) {
        this.confirmations = confirmations;
    }


    @Override
    public String toString() {
        return "TransactionDetail{" +
                "blockNumber='" + blockNumber + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", hash='" + hash + '\'' +
                ", nonce='" + nonce + '\'' +
                ", blockHash='" + blockHash + '\'' +
                ", transactionIndex='" + transactionIndex + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", value='" + value + '\'' +
                ", gas='" + gas + '\'' +
                ", gasPrice='" + gasPrice + '\'' +
                ", isError='" + isError + '\'' +
                ", txreceipt_status='" + txreceipt_status + '\'' +
                ", input='" + input + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", cumulativeGasUsed='" + cumulativeGasUsed + '\'' +
                ", gasUsed='" + gasUsed + '\'' +
                ", confirmations='" + confirmations + '\'' +
                '}';
    }
}
