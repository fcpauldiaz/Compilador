// Generated from /Users/usuario/NetBeansProjects/Antlr4/src/antlr4/program.g4 by ANTLR 4.4
package antlr4;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link programParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface programVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link programParser#structDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStructDeclaration(@NotNull programParser.StructDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(@NotNull programParser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull programParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#bool_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBool_literal(@NotNull programParser.Bool_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#rel_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel_op(@NotNull programParser.Rel_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull programParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#eq_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq_op(@NotNull programParser.Eq_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#int_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInt_literal(@NotNull programParser.Int_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#varType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarType(@NotNull programParser.VarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#methodType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodType(@NotNull programParser.MethodTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#uniExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUniExpr(@NotNull programParser.UniExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#sumExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSumExpr(@NotNull programParser.SumExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(@NotNull programParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(@NotNull programParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull programParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#cond_op1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond_op1(@NotNull programParser.Cond_op1Context ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#float_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloat_literal(@NotNull programParser.Float_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull programParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr2(@NotNull programParser.Expr2Context ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#exprRel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprRel(@NotNull programParser.ExprRelContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#arrayCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCall(@NotNull programParser.ArrayCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#op_plusmin}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_plusmin(@NotNull programParser.Op_plusminContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#char_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChar_literal(@NotNull programParser.Char_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#cond_op2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond_op2(@NotNull programParser.Cond_op2Context ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#parameterType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterType(@NotNull programParser.ParameterTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull programParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#exprEq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprEq(@NotNull programParser.ExprEqContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#valor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValor(@NotNull programParser.ValorContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#arg_exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_exp(@NotNull programParser.Arg_expContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(@NotNull programParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(@NotNull programParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#string_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_literal(@NotNull programParser.String_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#op_divmul}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOp_divmul(@NotNull programParser.Op_divmulContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#exprMul}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprMul(@NotNull programParser.ExprMulContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#arg_comma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg_comma(@NotNull programParser.Arg_commaContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#location}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocation(@NotNull programParser.LocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(@NotNull programParser.MethodCallContext ctx);
}