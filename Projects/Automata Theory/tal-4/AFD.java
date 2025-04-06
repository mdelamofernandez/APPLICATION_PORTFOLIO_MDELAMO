package tal;
import java.io.*;
import static tal.Token.Type.*;

/**
 * Analizador léxico implementado mediante un
 * autómata finito determinista.
 * <p>Cada estado del autómata se implementa con un objeto Runnable.
 */
public class AFD extends ALex
{
/**
 * Construye el autómata.
 * @param fichero Fichero de texto que se debe analizar.
 * @throws IOException
 */
public AFD(String fichero) throws IOException
{
    super(fichero);
    setStart(this::inicio);
}

private void inicio()
{
    if(isDigitChar())
        state(this::intval);
    else if(isChar('"'))
        stateNoChar(this::strval);
    else if (isChar('e'))
        state(this::entero_e1);
    else if (isChar('c'))
        state(this::cadena_c);
    else if (isChar('s'))
        state(this::si_s);
    else if (isChar('m'))
        state(this::mientras_m);
    else if(isChar('f'))
        state(this::fin_f);
    else if (isChar('i'))
        state(this::imprimir_i1);
    else if (isChar(':'))
        state(this::asign_1);
    else if(isChar('+'))
        state(this::sum_op);
    else if(isChar('-'))
        state(this::sum_op);
    else if(isChar('*'))
        state(this::mul_op);
    else if(isChar('/'))
        state(this::mul_op_div);
    else if(isChar('='))
        state(this::igual);
    else if(isChar('<'))
        state(this::menor_que);
    else if(isChar('>'))
        state(this::mayor_que);
    else if(isChar('!'))
        state(this::negacion);
    else if(isChar('|'))
        state(this::or_condicional);
    else if(isChar('&'))
        state(this::and_condicional);
    else if(isChar('('))
        state(this::ipar);
    else if(isChar(')'))
        state(this::dpar);
    else if(isIdCharStart())
        state(this::id);
    else if(isSpaceChar())
        restart();
    else if(isEofChar())
        token(EOF);
    else
        error();
}
private void id() {
    if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void entero_e1() {
    if(isChar('n'))
        state(this::entero_n);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void entero_n() {
    if(isChar('t'))
        state(this::entero_t);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void entero_t() {
    if(isChar('e'))
        state(this::entero_e2);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void entero_e2() {
    if(isChar('r'))
        state(this::entero_r);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void entero_r() {
    if(isChar('o'))
        state(this::entero_o);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void entero_o() {
    if (isIdChar())
        state(this::id);
    else
        token(ENTERO);
}
private void cadena_c(){
    if(isChar('a'))
        state(this::cadena_a1);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void cadena_a1(){
    if(isChar('d'))
        state(this::cadena_d);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void cadena_d(){
    if(isChar('e'))
        state(this::cadena_e);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void cadena_e(){
    if(isChar('n'))
        state(this::cadena_n);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void cadena_n(){
    if(isChar('a'))
        state(this::cadena_a2);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void cadena_a2(){
    if(isIdChar())
        state(this::id);
    else
        token(CADENA);
}

private void si_s() {
    if(isChar('i'))
        state(this::si_i);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void si_i() {
    if(isChar('n'))
        state(this::sino_n);
    else if(isIdChar())
        state(this::id);
    else
        token(SI);
}

private void sino_n() {
    if (isChar('o'))
        state(this::sino_o);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void sino_o() {
    if (isIdChar())
        state(this::id);
    else
        token(SINO);
}
private void mientras_m(){
    if (isChar('i'))
        state(this::mientras_i);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_i(){
    if (isChar('e'))
        state(this::mientras_e);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_e(){
    if (isChar('n'))
        state(this::mientras_n);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_n(){
    if (isChar('t'))
        state(this::mientras_t);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_t(){
    if (isChar('r'))
        state(this::mientras_r);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_r(){
    if (isChar('a'))
        state(this::mientras_a);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_a(){
    if (isChar('s'))
        state(this::mientras_s);
    else if (isIdChar())
        state(this::id);
    else
        token(ID);
}
private void mientras_s(){
    if (isIdChar())
        state(this::id);
    else
        token(MIENTRAS);
}

private void fin_f() {
    if(isChar('i'))
        state(this::fin_i);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void fin_i() {
    if(isChar('n'))
        state(this::fin_n);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}

private void fin_n() {
    if(isIdChar())
        state(this::id);
    else
        token(FIN);
}
private void imprimir_i1(){
    if(isChar('m'))
        state(this::imprimir_m1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_m1(){
    if(isChar('p'))
        state(this::imprimir_p);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_p(){
    if(isChar('r'))
        state(this::imprimir_r1);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_r1(){
    if(isChar('i'))
        state(this::imprimir_i2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_i2(){
    if(isChar('m'))
        state(this::imprimir_m2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_m2(){
    if(isChar('i'))
        state(this::imprimir_i3);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_i3(){
    if(isChar('r'))
        state(this::imprimir_r2);
    else if(isIdChar())
        state(this::id);
    else
        token(ID);
}
private void imprimir_r2(){
    if(isIdChar())
        state(this::id);
    else
        token(IMPRIMIR);
}
private void asign_1() {
    if (isChar('='))
        state(this::asign_2);
    else
        error();
}
private void asign_2(){
    if (isIdChar())
        error();
    else
        token(ASIGN);
}
private void sum_op() {
    token(SUM);
}
private void mul_op() {
    token(MUL);
}
private void mul_op_div() {
    if(isChar('/'))
        stateNoChar(this::comentario);
    else if(isChar('*'))
        stateNoChar(this::comentario_multilinea);
    else
        token(MUL);
}
private void comentario() {
    if(isChar('\n') || isEofChar())
        restart();
    else
        stateNoChar(this::comentario);
}

private void comentario_multilinea() {
    if(isChar('*'))
        stateNoChar(this::comentario_multilinea_fin);
    else if(isEofChar())
        error();
    else
        stateNoChar(this::comentario_multilinea);
}
private void comentario_multilinea_fin() {
     if (isChar('/'))
         restart();
     else if(isChar('*'))
        stateNoChar(this::comentario_multilinea_fin);
    else if(isEofChar())
        error();
    else
        stateNoChar(this::comentario_multilinea);
}
private void igual(){
    token(REL);
}
private void menor_que(){
    if (isChar('=') || isChar('>'))
        state(this::igual);
    else
        token(REL);
}

private void mayor_que(){
    if (isChar('='))
        state(this::igual);
    else
        token(REL);
}
private void negacion(){
    token(NEG);
}
private void or_condicional() {
    if(isChar('|'))
        state(this::or_condicional_fin);
    else
        error();
}
private void or_condicional_fin() {
    token(OR);
}
private void and_condicional() {
    if(isChar('&'))
        state(this::and_condicional_fin);
    else
        error();
}
private void and_condicional_fin() {
    token(AND);
}
private void ipar() {
    token(IPAR);
}
private void dpar() {token(DPAR);}

private void intval()
{
    if(isDigitChar())
        state(this::intval);
    else if(isIdChar())
        error();
    else
        token(INTVAL);
}
private void strval()
{
    if(isChar('\n'))
        error();
    else if(isChar('"'))
        stateNoChar(this::strval_fin);
    else
        state(this::strval);
}

private void strval_fin() { token(STRVAL);}
} // AFD
