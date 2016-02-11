// Generated from /Users/usuario/NetBeansProjects/Antlr4/src/antlr4/program.g4 by ANTLR 4.4
package antlr4;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link programParser}.
 */
public interface programListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link programParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructDeclaration(@NotNull programParser.StructDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#structDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructDeclaration(@NotNull programParser.StructDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull programParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull programParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(@NotNull programParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(@NotNull programParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#bool_literal}.
	 * @param ctx the parse tree
	 */
	void enterBool_literal(@NotNull programParser.Bool_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#bool_literal}.
	 * @param ctx the parse tree
	 */
	void exitBool_literal(@NotNull programParser.Bool_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void enterRel_op(@NotNull programParser.Rel_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#rel_op}.
	 * @param ctx the parse tree
	 */
	void exitRel_op(@NotNull programParser.Rel_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(@NotNull programParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(@NotNull programParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#eq_op}.
	 * @param ctx the parse tree
	 */
	void enterEq_op(@NotNull programParser.Eq_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#eq_op}.
	 * @param ctx the parse tree
	 */
	void exitEq_op(@NotNull programParser.Eq_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void enterInt_literal(@NotNull programParser.Int_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void exitInt_literal(@NotNull programParser.Int_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#varType}.
	 * @param ctx the parse tree
	 */
	void enterVarType(@NotNull programParser.VarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#varType}.
	 * @param ctx the parse tree
	 */
	void exitVarType(@NotNull programParser.VarTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#methodType}.
	 * @param ctx the parse tree
	 */
	void enterMethodType(@NotNull programParser.MethodTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#methodType}.
	 * @param ctx the parse tree
	 */
	void exitMethodType(@NotNull programParser.MethodTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#uniExpr}.
	 * @param ctx the parse tree
	 */
	void enterUniExpr(@NotNull programParser.UniExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#uniExpr}.
	 * @param ctx the parse tree
	 */
	void exitUniExpr(@NotNull programParser.UniExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#sumExpr}.
	 * @param ctx the parse tree
	 */
	void enterSumExpr(@NotNull programParser.SumExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#sumExpr}.
	 * @param ctx the parse tree
	 */
	void exitSumExpr(@NotNull programParser.SumExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(@NotNull programParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(@NotNull programParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#arg}.
	 * @param ctx the parse tree
	 */
	void enterArg(@NotNull programParser.ArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#arg}.
	 * @param ctx the parse tree
	 */
	void exitArg(@NotNull programParser.ArgContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull programParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull programParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#cond_op1}.
	 * @param ctx the parse tree
	 */
	void enterCond_op1(@NotNull programParser.Cond_op1Context ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#cond_op1}.
	 * @param ctx the parse tree
	 */
	void exitCond_op1(@NotNull programParser.Cond_op1Context ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#float_literal}.
	 * @param ctx the parse tree
	 */
	void enterFloat_literal(@NotNull programParser.Float_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#float_literal}.
	 * @param ctx the parse tree
	 */
	void exitFloat_literal(@NotNull programParser.Float_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(@NotNull programParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(@NotNull programParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterExpr2(@NotNull programParser.Expr2Context ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitExpr2(@NotNull programParser.Expr2Context ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#exprRel}.
	 * @param ctx the parse tree
	 */
	void enterExprRel(@NotNull programParser.ExprRelContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#exprRel}.
	 * @param ctx the parse tree
	 */
	void exitExprRel(@NotNull programParser.ExprRelContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#arrayCall}.
	 * @param ctx the parse tree
	 */
	void enterArrayCall(@NotNull programParser.ArrayCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#arrayCall}.
	 * @param ctx the parse tree
	 */
	void exitArrayCall(@NotNull programParser.ArrayCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#op_plusmin}.
	 * @param ctx the parse tree
	 */
	void enterOp_plusmin(@NotNull programParser.Op_plusminContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#op_plusmin}.
	 * @param ctx the parse tree
	 */
	void exitOp_plusmin(@NotNull programParser.Op_plusminContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void enterChar_literal(@NotNull programParser.Char_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void exitChar_literal(@NotNull programParser.Char_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#cond_op2}.
	 * @param ctx the parse tree
	 */
	void enterCond_op2(@NotNull programParser.Cond_op2Context ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#cond_op2}.
	 * @param ctx the parse tree
	 */
	void exitCond_op2(@NotNull programParser.Cond_op2Context ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#parameterType}.
	 * @param ctx the parse tree
	 */
	void enterParameterType(@NotNull programParser.ParameterTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#parameterType}.
	 * @param ctx the parse tree
	 */
	void exitParameterType(@NotNull programParser.ParameterTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull programParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull programParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#exprEq}.
	 * @param ctx the parse tree
	 */
	void enterExprEq(@NotNull programParser.ExprEqContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#exprEq}.
	 * @param ctx the parse tree
	 */
	void exitExprEq(@NotNull programParser.ExprEqContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#valor}.
	 * @param ctx the parse tree
	 */
	void enterValor(@NotNull programParser.ValorContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#valor}.
	 * @param ctx the parse tree
	 */
	void exitValor(@NotNull programParser.ValorContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#arg_exp}.
	 * @param ctx the parse tree
	 */
	void enterArg_exp(@NotNull programParser.Arg_expContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#arg_exp}.
	 * @param ctx the parse tree
	 */
	void exitArg_exp(@NotNull programParser.Arg_expContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(@NotNull programParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(@NotNull programParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull programParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull programParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#string_literal}.
	 * @param ctx the parse tree
	 */
	void enterString_literal(@NotNull programParser.String_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#string_literal}.
	 * @param ctx the parse tree
	 */
	void exitString_literal(@NotNull programParser.String_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#op_divmul}.
	 * @param ctx the parse tree
	 */
	void enterOp_divmul(@NotNull programParser.Op_divmulContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#op_divmul}.
	 * @param ctx the parse tree
	 */
	void exitOp_divmul(@NotNull programParser.Op_divmulContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#exprMul}.
	 * @param ctx the parse tree
	 */
	void enterExprMul(@NotNull programParser.ExprMulContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#exprMul}.
	 * @param ctx the parse tree
	 */
	void exitExprMul(@NotNull programParser.ExprMulContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#arg_comma}.
	 * @param ctx the parse tree
	 */
	void enterArg_comma(@NotNull programParser.Arg_commaContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#arg_comma}.
	 * @param ctx the parse tree
	 */
	void exitArg_comma(@NotNull programParser.Arg_commaContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#location}.
	 * @param ctx the parse tree
	 */
	void enterLocation(@NotNull programParser.LocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#location}.
	 * @param ctx the parse tree
	 */
	void exitLocation(@NotNull programParser.LocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(@NotNull programParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(@NotNull programParser.MethodCallContext ctx);
}