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
	 * Enter a parse tree produced by the {@code statementReturn}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementReturn(@NotNull programParser.StatementReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementReturn}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementReturn(@NotNull programParser.StatementReturnContext ctx);
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
	 * Enter a parse tree produced by the {@code unaryExprNot}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExprNot(@NotNull programParser.UnaryExprNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExprNot}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExprNot(@NotNull programParser.UnaryExprNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relationExprAddExpr}
	 * labeled alternative in {@link programParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationExprAddExpr(@NotNull programParser.RelationExprAddExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationExprAddExpr}
	 * labeled alternative in {@link programParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationExprAddExpr(@NotNull programParser.RelationExprAddExprContext ctx);
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
	 * Enter a parse tree produced by the {@code expressionAndExpr}
	 * labeled alternative in {@link programParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionAndExpr(@NotNull programParser.ExpressionAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionAndExpr}
	 * labeled alternative in {@link programParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionAndExpr(@NotNull programParser.ExpressionAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementLocation}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementLocation(@NotNull programParser.StatementLocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementLocation}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementLocation(@NotNull programParser.StatementLocationContext ctx);
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
	 * Enter a parse tree produced by {@link programParser#cond_op_or}.
	 * @param ctx the parse tree
	 */
	void enterCond_op_or(@NotNull programParser.Cond_op_orContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#cond_op_or}.
	 * @param ctx the parse tree
	 */
	void exitCond_op_or(@NotNull programParser.Cond_op_orContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionCondOpOr}
	 * labeled alternative in {@link programParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpressionCondOpOr(@NotNull programParser.ExpressionCondOpOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionCondOpOr}
	 * labeled alternative in {@link programParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpressionCondOpOr(@NotNull programParser.ExpressionCondOpOrContext ctx);
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
	 * Enter a parse tree produced by the {@code andExprCondOpAnd}
	 * labeled alternative in {@link programParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExprCondOpAnd(@NotNull programParser.AndExprCondOpAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExprCondOpAnd}
	 * labeled alternative in {@link programParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExprCondOpAnd(@NotNull programParser.AndExprCondOpAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExprEqExpr}
	 * labeled alternative in {@link programParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExprEqExpr(@NotNull programParser.AndExprEqExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExprEqExpr}
	 * labeled alternative in {@link programParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExprEqExpr(@NotNull programParser.AndExprEqExprContext ctx);
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
	 * Enter a parse tree produced by the {@code valueMethodCall}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValueMethodCall(@NotNull programParser.ValueMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueMethodCall}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValueMethodCall(@NotNull programParser.ValueMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parameterID}
	 * labeled alternative in {@link programParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameterID(@NotNull programParser.ParameterIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parameterID}
	 * labeled alternative in {@link programParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameterID(@NotNull programParser.ParameterIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueExprWithParent}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValueExprWithParent(@NotNull programParser.ValueExprWithParentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueExprWithParent}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValueExprWithParent(@NotNull programParser.ValueExprWithParentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueLocation}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValueLocation(@NotNull programParser.ValueLocationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueLocation}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValueLocation(@NotNull programParser.ValueLocationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExprCast}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExprCast(@NotNull programParser.UnaryExprCastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExprCast}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExprCast(@NotNull programParser.UnaryExprCastContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#minusplus_op}.
	 * @param ctx the parse tree
	 */
	void enterMinusplus_op(@NotNull programParser.Minusplus_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#minusplus_op}.
	 * @param ctx the parse tree
	 */
	void exitMinusplus_op(@NotNull programParser.Minusplus_opContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExprUnary}
	 * labeled alternative in {@link programParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExprUnary(@NotNull programParser.MultExprUnaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExprUnary}
	 * labeled alternative in {@link programParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExprUnary(@NotNull programParser.MultExprUnaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExprMinus}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExprMinus(@NotNull programParser.UnaryExprMinusContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExprMinus}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExprMinus(@NotNull programParser.UnaryExprMinusContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclarationArray}
	 * labeled alternative in {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclarationArray(@NotNull programParser.VarDeclarationArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclarationArray}
	 * labeled alternative in {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclarationArray(@NotNull programParser.VarDeclarationArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementWhile}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementWhile(@NotNull programParser.StatementWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementWhile}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementWhile(@NotNull programParser.StatementWhileContext ctx);
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
	 * Enter a parse tree produced by the {@code eqExprRelationExpr}
	 * labeled alternative in {@link programParser#eqExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqExprRelationExpr(@NotNull programParser.EqExprRelationExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExprRelationExpr}
	 * labeled alternative in {@link programParser#eqExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqExprRelationExpr(@NotNull programParser.EqExprRelationExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code valueLiteral}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValueLiteral(@NotNull programParser.ValueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code valueLiteral}
	 * labeled alternative in {@link programParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValueLiteral(@NotNull programParser.ValueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementBlock}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlock(@NotNull programParser.StatementBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementBlock}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlock(@NotNull programParser.StatementBlockContext ctx);
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
	 * Enter a parse tree produced by the {@code parameterArray}
	 * labeled alternative in {@link programParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameterArray(@NotNull programParser.ParameterArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parameterArray}
	 * labeled alternative in {@link programParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameterArray(@NotNull programParser.ParameterArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExprMultDivOp}
	 * labeled alternative in {@link programParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultExprMultDivOp(@NotNull programParser.MultExprMultDivOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExprMultDivOp}
	 * labeled alternative in {@link programParser#multExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultExprMultDivOp(@NotNull programParser.MultExprMultDivOpContext ctx);
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
	 * Enter a parse tree produced by the {@code statementMethodCall}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementMethodCall(@NotNull programParser.StatementMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementMethodCall}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementMethodCall(@NotNull programParser.StatementMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementIF}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementIF(@NotNull programParser.StatementIFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementIF}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementIF(@NotNull programParser.StatementIFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqExprEqOp}
	 * labeled alternative in {@link programParser#eqExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqExprEqOp(@NotNull programParser.EqExprEqOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqExprEqOp}
	 * labeled alternative in {@link programParser#eqExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqExprEqOp(@NotNull programParser.EqExprEqOpContext ctx);
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
	 * Enter a parse tree produced by {@link programParser#cond_op_and}.
	 * @param ctx the parse tree
	 */
	void enterCond_op_and(@NotNull programParser.Cond_op_andContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#cond_op_and}.
	 * @param ctx the parse tree
	 */
	void exitCond_op_and(@NotNull programParser.Cond_op_andContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#locationMember}.
	 * @param ctx the parse tree
	 */
	void enterLocationMember(@NotNull programParser.LocationMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#locationMember}.
	 * @param ctx the parse tree
	 */
	void exitLocationMember(@NotNull programParser.LocationMemberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statementExpression}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatementExpression(@NotNull programParser.StatementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statementExpression}
	 * labeled alternative in {@link programParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatementExpression(@NotNull programParser.StatementExpressionContext ctx);
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
	 * Enter a parse tree produced by the {@code addExprMinusPlusOp}
	 * labeled alternative in {@link programParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExprMinusPlusOp(@NotNull programParser.AddExprMinusPlusOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExprMinusPlusOp}
	 * labeled alternative in {@link programParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExprMinusPlusOp(@NotNull programParser.AddExprMinusPlusOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExprOther}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExprOther(@NotNull programParser.UnaryExprOtherContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExprOther}
	 * labeled alternative in {@link programParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExprOther(@NotNull programParser.UnaryExprOtherContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#powmod_op}.
	 * @param ctx the parse tree
	 */
	void enterPowmod_op(@NotNull programParser.Powmod_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#powmod_op}.
	 * @param ctx the parse tree
	 */
	void exitPowmod_op(@NotNull programParser.Powmod_opContext ctx);
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
	 * Enter a parse tree produced by the {@code relationExprRelOp}
	 * labeled alternative in {@link programParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void enterRelationExprRelOp(@NotNull programParser.RelationExprRelOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relationExprRelOp}
	 * labeled alternative in {@link programParser#relationExpr}.
	 * @param ctx the parse tree
	 */
	void exitRelationExprRelOp(@NotNull programParser.RelationExprRelOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclarationID}
	 * labeled alternative in {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclarationID(@NotNull programParser.VarDeclarationIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclarationID}
	 * labeled alternative in {@link programParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclarationID(@NotNull programParser.VarDeclarationIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addExprMultExpr}
	 * labeled alternative in {@link programParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void enterAddExprMultExpr(@NotNull programParser.AddExprMultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addExprMultExpr}
	 * labeled alternative in {@link programParser#addExpr}.
	 * @param ctx the parse tree
	 */
	void exitAddExprMultExpr(@NotNull programParser.AddExprMultExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#multdiv_op}.
	 * @param ctx the parse tree
	 */
	void enterMultdiv_op(@NotNull programParser.Multdiv_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#multdiv_op}.
	 * @param ctx the parse tree
	 */
	void exitMultdiv_op(@NotNull programParser.Multdiv_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link programParser#statementElse}.
	 * @param ctx the parse tree
	 */
	void enterStatementElse(@NotNull programParser.StatementElseContext ctx);
	/**
	 * Exit a parse tree produced by {@link programParser#statementElse}.
	 * @param ctx the parse tree
	 */
	void exitStatementElse(@NotNull programParser.StatementElseContext ctx);
}