package com.example.ethereum_wallet;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Auction extends Contract {
    public static final String BINARY = "60006017819055601680546001600160a01b0319163317905560c06040526080818152606060a0819052600d928355805190929061004190600e9084906100ef565b5090505060405180604001604052806001815260200182815250600d60016003811061006957fe5b600202016000820151816000015560208201518160010190805190602001906100939291906100ef565b5090505060405180604001604052806002815260200182815250600d6002600381106100bb57fe5b600202016000820151816000015560208201518160010190805190602001906100e59291906100ef565b5090505050610157565b82805482825590600052602060002090810192821561012a579160200282015b8281111561012a57825182559160200191906001019061010f565b5061013692915061013a565b5090565b61015491905b808211156101365760008155600101610140565b90565b6104e9806101666000396000f3fe6080604052600436106100705760003560e01c8063609817051161004e57806360981705146100d3578063952587d614610124578063a2fb117514610139578063bfb231d21461016357610070565b80631aa3a0081461007557806338af3eed1461007f578063598647f8146100b0575b600080fd5b61007d61019f565b005b34801561008b57600080fd5b50610094610275565b604080516001600160a01b039092168252519081900360200190f35b61007d600480360360408110156100c657600080fd5b5080359060200135610284565b3480156100df57600080fd5b506100fd600480360360208110156100f657600080fd5b503561033b565b6040805193845260208401929092526001600160a01b031682820152519081900360600190f35b34801561013057600080fd5b5061007d61039c565b34801561014557600080fd5b506100946004803603602081101561015c57600080fd5b5035610480565b34801561016f57600080fd5b5061018d6004803603602081101561018657600080fd5b503561049d565b60408051918252519081900360200190f35b601754600181600481106101af57fe5b6003020160010181905550336001601754600481106101ca57fe5b6003020160020160006101000a8154816001600160a01b0302191690836001600160a01b03160217905550600560016017546004811061020657fe5b60030201556017546001906004811061021b57fe5b3360009081526020819052604090206003919091029190910180548255600180820154818401556002918201549190920180546001600160a01b0319166001600160a01b0392909216919091179055601780549091019055565b6016546001600160a01b031681565b336000908152602081905260409020548111806102a15750600282115b156102ab57600080fd5b336000908152602081905260409020805482900380825560019182015490918291600481106102d657fe5b60030201600001819055506000600d84600381106102f057fe5b60020201905060005b8381101561033457336000908152602081815260408220600190810154818601805480840182559085529290932090910191909155016102f9565b5050505050565b60008060006001846004811061034d57fe5b60030201546001856004811061035f57fe5b60030201600101546001866004811061037457fe5b6003020160020160009054906101000a90046001600160a01b03169250925092509193909250565b6016546001600160a01b031633146103b357600080fd5b60005b600381101561047d576000600d82600381106103ce57fe5b60020201905080600101805490506000146104745760018101546000908043816103f457fe5b04816103fc57fe5b069050600082600101828154811061041057fe5b906000526020600020015490506001816004811061042a57fe5b6003020160020160009054906101000a90046001600160a01b03166013856003811061045257fe5b0180546001600160a01b0319166001600160a01b039290921691909117905550505b506001016103b6565b50565b6013816003811061048d57fe5b01546001600160a01b0316905081565b600d81600381106104aa57fe5b600202015490508156fea265627a7a7231582018419e55778df72763bd054ef63e9142d1177910e353a16ed4ed0ef3140bc1c864736f6c63430005100032";

    public static final String FUNC_BENEFICIARY = "beneficiary";

    public static final String FUNC_BID = "bid";

    public static final String FUNC_GETPERSONDETAILS = "getPersonDetails";

    public static final String FUNC_ITEMS = "items";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_REVEALWINNERS = "revealWinners";

    public static final String FUNC_WINNERS = "winners";

    @Deprecated
    protected Auction(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Auction(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Auction(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Auction(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> beneficiary() {
        final Function function = new Function(FUNC_BENEFICIARY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> bid(BigInteger _itemId, BigInteger _count, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_itemId), 
                new org.web3j.abi.datatypes.generated.Uint256(_count)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>> getPersonDetails(BigInteger id) {
        final Function function = new Function(FUNC_GETPERSONDETAILS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple3<BigInteger, BigInteger, String>>(function,
                new Callable<Tuple3<BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple3<BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> items(BigInteger param0) {
        final Function function = new Function(FUNC_ITEMS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> register(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> revealWinners() {
        final Function function = new Function(
                FUNC_REVEALWINNERS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> winners(BigInteger param0) {
        final Function function = new Function(FUNC_WINNERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static Auction load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Auction(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Auction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Auction(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Auction load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Auction(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Auction load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Auction(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Auction> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Auction.class, web3j, credentials, contractGasProvider, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<Auction> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(Auction.class, web3j, transactionManager, contractGasProvider, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Auction> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Auction.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<Auction> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Auction.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }
}
