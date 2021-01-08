package com.example.ethereum_wallet;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

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
public class Ballot extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610860380380610860833981" +
            "8101604052602081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b9" +
            "0830190602082018581111561006857600080fd5b825186602082028301116401000000008211171561008557600080fd5b825250815160" +
            "20918201928201910280838360005b838110156100b257818101518382015260200161009a565b50505050919091016040908152600080546001" +
            "600160a01b03191633178082556001600160a01b03168152600160208190529181209190915593505050505b815181101561015357600260405180604" +
            "0016040528084848151811061011357fe5b6020908102919091018101518252600091810182905283546001818101865594835291819020835160" +
            "0290930201918255919091015190820155016100f1565b50506106fc806101646000396000f3fe608060405234801561001057600080fd5b506004361" +
            "06100885760003560e01c8063609ff1bd1161005b578063609ff1bd1461012c5780639e7b8d6114610146578063a3ec138d1461016c578063e2ba53f0" +
            "146101c057610088565b80630121b93f1461008d578063013cf08b146100ac5780632e4176cf146100e25780635c19a95c14610106575b600080fd5b" +
            "6100aa600480360360208110156100a357600080fd5b50356101c8565b005b6100c9600480360360208110156100c257600080fd5b50356102b3565" +
            "b6040805192835260208301919091528051918290030190f35b6100ea6102de565b604080516001600160a01b039092168252519081900360200190f" +
            "35b6100aa6004803603602081101561011c57600080fd5b50356001600160a01b03166102ed565b6101346104dc565b604080519182525190819003" +
            "60200190f35b6100aa6004803603602081101561015c57600080fd5b50356001600160a01b0316610543565b6101926004803603602081101561018257600080fd5b50356001600160a01b031661063e565b6040805194855292151560208501526001600160a01b03909116838301526060830152519081900360800190f35b610134610672565b3360009081526001602052604090208054610221576040805162461bcd60e51b8152602060048201526014602482015273486173206e6f20726967687420746f20766f746560601b604482015290519081900360640190fd5b600181015460ff161561026c576040805162461bcd60e51b815260206004820152600e60248201526d20b63932b0b23c903b37ba32b21760911b604482015290519081900360640190fd5b6001818101805460ff191690911790556002808201839055815481549091908490811061029557fe5b60009182526020909120600160029092020101805490910190555050565b600281815481106102c057fe5b60009182526020909120600290910201805460019091015490915082565b6000546001600160a01b031681565b3360009081526001602081905260409091209081015460ff161561034d576040805162461bcd60e51b81526020600482015260126024820152712cb7ba9030b63932b0b23c903b37ba32b21760711b604482015290519081900360640190fd5b6001600160a01b0382163314156103ab576040805162461bcd60e51b815260206004820152601e60248201527f53656c662d64656c65676174696f6e20697320646973616c6c6f7765642e0000604482015290519081900360640190fd5b6001600160a01b038281166000908152600160208190526040909120015461010090041615610455576001600160a01b039182166000908152600160208190526040909120015461010090049091169033821415610450576040805162461bcd60e51b815260206004820152601960248201527f466f756e64206c6f6f7020696e2064656c65676174696f6e2e00000000000000604482015290519081900360640190fd5b6103ab565b6001818101805460ff19168217610100600160a81b0319166101006001600160a01b0386169081029190911790915560009081526020829052604090209081015460ff16156104cf578154600282810154815481106104b057fe5b60009182526020909120600160029092020101805490910190556104d7565b815481540181555b505050565b600080805b60025481101561053e5781600282815481106104f957fe5b9060005260206000209060020201600101541115610536576002818154811061051e57fe5b90600052602060002090600202016001015491508092505b6001016104e1565b505090565b6000546001600160a01b0316331461058c5760405162461bcd60e51b81526004018080602001828103825260288152602001806106a06028913960400191505060405180910390fd5b6001600160a01b0381166000908152600160208190526040909120015460ff16156105fe576040805162461bcd60e51b815260206004820152601860248201527f54686520766f74657220616c726561647920766f7465642e0000000000000000604482015290519081900360640190fd5b6001600160a01b0381166000908152600160205260409020541561062157600080fd5b6001600160a01b0316600090815260016020819052604090912055565b600160208190526000918252604090912080549181015460029091015460ff82169161010090046001600160a01b03169084565b6000600261067e6104dc565b8154811061068857fe5b90600052602060002090600202016000015490509056fe4f6e6c79206368616972706572736f6e2063616e206769766520726967687420746f20766f74652ea265627a7a72315820bd9d3f48f4e81f56e2760bdd66a3afdf554e7d38d6ad82f48359454e009aaa0064736f6c63430005100032";

    public static final String FUNC_CHAIRPERSON = "chairperson";

    public static final String FUNC_DELEGATE = "delegate";

    public static final String FUNC_GIVERIGHTTOVOTE = "giveRightToVote";

    public static final String FUNC_PROPOSALS = "proposals";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_VOTERS = "voters";

    public static final String FUNC_WINNERNAME = "winnerName";

    public static final String FUNC_WINNINGPROPOSAL = "winningProposal";

    @Deprecated
    protected Ballot(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Ballot(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Ballot(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Ballot(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> chairperson() {
        final Function function = new Function(FUNC_CHAIRPERSON, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> delegate(String to) {
        final Function function = new Function(
                FUNC_DELEGATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> giveRightToVote(String voter) {
        final Function function = new Function(
                FUNC_GIVERIGHTTOVOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, voter)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<byte[], BigInteger>> proposals(BigInteger param0) {
        final Function function = new Function(FUNC_PROPOSALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple2<byte[], BigInteger>>(function,
                new Callable<Tuple2<byte[], BigInteger>>() {
                    @Override
                    public Tuple2<byte[], BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> vote(BigInteger proposal) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposal)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<BigInteger, Boolean, String, BigInteger>> voters(String param0) {
        final Function function = new Function(FUNC_VOTERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple4<BigInteger, Boolean, String, BigInteger>>(function,
                new Callable<Tuple4<BigInteger, Boolean, String, BigInteger>>() {
                    @Override
                    public Tuple4<BigInteger, Boolean, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<BigInteger, Boolean, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<byte[]> winnerName() {
        final Function function = new Function(FUNC_WINNERNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> winningProposal() {
        final Function function = new Function(FUNC_WINNINGPROPOSAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @Deprecated
    public static Ballot load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Ballot load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Ballot(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Ballot load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Ballot(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Ballot load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Ballot(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Ballot> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<byte[]> proposalNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proposalNames, org.web3j.abi.datatypes.generated.Bytes32.class))));
        return deployRemoteCall(Ballot.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Ballot> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<byte[]> proposalNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proposalNames, org.web3j.abi.datatypes.generated.Bytes32.class))));
        return deployRemoteCall(Ballot.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Ballot> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<byte[]> proposalNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proposalNames, org.web3j.abi.datatypes.generated.Bytes32.class))));
        return deployRemoteCall(Ballot.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Ballot> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<byte[]> proposalNames) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proposalNames, org.web3j.abi.datatypes.generated.Bytes32.class))));
        return deployRemoteCall(Ballot.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
