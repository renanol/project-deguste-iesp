package br.com.degust.report;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import br.com.deguste.util.FacesUtil;

public class RelatorioGenerico {

	private String caminhoRelatorioCompilado;

	private String caminhoImagemSistema;

	private String caminhoImagemPMJP;

	private String nomeDoRelatorio;

	private String query;

	private HashMap<String, Object> parametros;

	private JasperReport relatorio;

	private JasperPrint impressao;

	public RelatorioGenerico(String nomeDoRelatorio) {
		this.nomeDoRelatorio = nomeDoRelatorio;
		this.caminhoRelatorioCompilado = FacesUtil.obterCaminhoReal("reports/jasper/" + nomeDoRelatorio + ".jasper");
		this.caminhoImagemSistema = FacesUtil.obterCaminhoReal("/imagem/umti.png");
		this.caminhoImagemPMJP = FacesUtil.obterCaminhoReal("/imagem/logo_nova_pmjp.png");
		this.parametros = new HashMap<String, Object>();
		montarParametrosGenericosRelatorio();
	}

	public final void montarParametrosGenericosRelatorio() {
		parametros.put("logoSistema", caminhoImagemSistema);
		parametros.put("logoPMJP", caminhoImagemPMJP);
	}

	public String getCaminhoRelatorioCompilado() {
		return caminhoRelatorioCompilado;
	}

	public void setCaminhoRelatorioCompilado(String caminhoRelatorioCompilado) {
		this.caminhoRelatorioCompilado = caminhoRelatorioCompilado;
	}

	public String getNomeDoRelatorio() {
		return nomeDoRelatorio;
	}

	public void setNomeDoRelatorio(String nomeDoRelatorio) {
		this.nomeDoRelatorio = nomeDoRelatorio;
	}

	public HashMap<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(HashMap<String, Object> parametros) {
		this.parametros = parametros;
		montarParametrosGenericosRelatorio();
	}

	public JasperReport getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(JasperReport relatorio) {
		this.relatorio = relatorio;
	}

	public JasperPrint getImpressao() {
		return impressao;
	}

	public void setImpressao(JasperPrint impressao) {
		this.impressao = impressao;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getCaminhoImagemSistema() {
		return caminhoImagemSistema;
	}

	public void setCaminhoImagemSistema(String caminhoImagemSistema) {
		this.caminhoImagemSistema = caminhoImagemSistema;
	}

	public String getCaminhoImagemPMJP() {
		return caminhoImagemPMJP;
	}

	public void setCaminhoImagemPMJP(String caminhoImagemPMJP) {
		this.caminhoImagemPMJP = caminhoImagemPMJP;
	}
}
