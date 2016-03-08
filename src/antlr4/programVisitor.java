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
	 * Visit a parse tree produced by the {@code statementReturn}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementReturn(@NotNull programParser.StatementReturnContext ctx);
	/**
	 * Visit a parse tree produced by the {@code locationMemberArray}
	 * labeled alternative in {@link programParser#locationMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocationMemberArray(@NotNull programParser.LocationMemberArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementLocationArray}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementLocationArray(@NotNull programParser.StatementLocationArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(@NotNull programParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExprNot}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExprNot(@NotNull programParser.UnaryExprNotContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#rel_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel_op(@NotNull programParser.Rel_opContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionAndExpr}
	 * labeled alternative in {@link programParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionAndExpr(@NotNull programParser.ExpressionAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementLocation}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementLocation(@NotNull programParser.StatementLocationContext ctx);
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
	 * Visit a parse tree produced by {@link programParser#cond_op_or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond_op_or(@NotNull programParser.Cond_op_orContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionCondOpOr}
	 * labeled alternative in {@link programParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionCondOpOr(@NotNull programParser.ExpressionCondOpOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(@NotNull programParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExprCondOpAnd}
	 * labeled alternative in {@link programParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExprCondOpAnd(@NotNull programParser.AndExprCondOpAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExprEqExpr}
	 * labeled alternative in {@link programParser#andExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExprEqExpr(@NotNull programParser.AndExprEqExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#char_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChar_literal(@NotNull programParser.Char_literalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueMethodCall}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueMethodCall(@NotNull programParser.ValueMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parameterID}
	 * labeled alternative in {@link programParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterID(@NotNull programParser.ParameterIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueExprWithParent}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExprWithParent(@NotNull programParser.ValueExprWithParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueLocation}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueLocation(@NotNull programParser.ValueLocationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExprCast}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExprCast(@NotNull programParser.UnaryExprCastContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#minusplus_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinusplus_op(@NotNull programParser.Minusplus_opContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExprUnary}
	 * labeled alternative in {@link programParser#multExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExprUnary(@NotNull programParser.MultExprUnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#locationMethod}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocationMethod(@NotNull programParser.LocationMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExprMinus}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExprMinus(@NotNull programParser.UnaryExprMinusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDeclarationArray}
	 * labeled alternative in {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclarationArray(@NotNull programParser.VarDeclarationArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementWhile}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementWhile(@NotNull programParser.StatementWhileContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#methodCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCall(@NotNull programParser.MethodCallContext ctx);
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
	 * Visit a parse tree produced by the {@code eqExprRelationExpr}
	 * labeled alternative in {@link programParser#eqExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExprRelationExpr(@NotNull programParser.EqExprRelationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueLiteral}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueLiteral(@NotNull programParser.ValueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementBlock}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlock(@NotNull programParser.StatementBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#boolean_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean_literal(@NotNull programParser.Boolean_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull programParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#methodType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodType(@NotNull programParser.MethodTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parameterArray}
	 * labeled alternative in {@link programParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterArray(@NotNull programParser.ParameterArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code locationMemberMethod}
	 * labeled alternative in {@link programParser#locationMember}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocationMemberMethod(@NotNull programParser.LocationMemberMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExprMultDivOp}
	 * labeled alternative in {@link programParser#multExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExprMultDivOp(@NotNull programParser.MultExprMultDivOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#arg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArg(@NotNull programParser.ArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementMethodCall}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementMethodCall(@NotNull programParser.StatementMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementIF}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementIF(@NotNull programParser.StatementIFContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#relationExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationExpr(@NotNull programParser.RelationExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eqExprEqOp}
	 * labeled alternative in {@link programParser#eqExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqExprEqOp(@NotNull programParser.EqExprEqOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#parameterType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterType(@NotNull programParser.ParameterTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#cond_op_and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCond_op_and(@NotNull programParser.Cond_op_andContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#locationArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocationArray(@NotNull programParser.LocationArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code statementExpression}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpression(@NotNull programParser.StatementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(@NotNull programParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExprMinusPlusOp}
	 * labeled alternative in {@link programParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExprMinusPlusOp(@NotNull programParser.AddExprMinusPlusOpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExprOther}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExprOther(@NotNull programParser.UnaryExprOtherContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#powmod_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowmod_op(@NotNull programParser.Powmod_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#location}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocation(@NotNull programParser.LocationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDeclarationID}
	 * labeled alternative in {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclarationID(@NotNull programParser.VarDeclarationIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addExprMultExpr}
	 * labeled alternative in {@link programParser#addExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddExprMultExpr(@NotNull programParser.AddExprMultExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valueLocationArray}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueLocationArray(@NotNull programParser.ValueLocationArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#multdiv_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultdiv_op(@NotNull programParser.Multdiv_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link programParser#statementElse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementElse(@NotNull programParser.StatementElseContext ctx);
}