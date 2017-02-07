package br.com.cezar.webfront.hibernate;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

public abstract class HibernateUtil {
	private static SessionFactory fabricaSessao = null;
	private static Session sessao;
	private static Transaction transacao;
	private static Configuration hibernateConfig;
	private static ServiceRegistry serviceRegistry;

	/**
	 * Inicia a SessionFactory
	 */

	static {
		try {
			fabricaSessao = getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		if (fabricaSessao == null) {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			fabricaSessao = configuration.buildSessionFactory(serviceRegistry);
			fabricaSessao.openSession();
			return fabricaSessao;
		}
		return fabricaSessao;
	}

	public static Session getSession() {
		return fabricaSessao.openSession();
	}

	public static void gerarBanco() {
		// Carrega as configurações do arquivo
		// hibernate.cfg.xml
		Configuration conf = new Configuration();
		conf.configure();
		SchemaExport se2 = new SchemaExport(conf);
		// Executa a operação da criação do Banco de Dados
		se2.create(true, true);
	}

	/**
	 * Retorna uma nova sessão
	 * 
	 * @return Session
	 */
	private static Session getSessao() {
		return fabricaSessao.openSession();
	}

	/**
	 * Apaga e cria novamente o Schema do Banco de Dados
	 */
	public static void resetarBD() {
		try {
			SchemaExport se = new SchemaExport(hibernateConfig);
			se.drop(true, true);
			se.create(true, true);
		} catch (Exception e) {
		}
	}

	/**
	 * Atualiza o Schema do Banco de Dados
	 */
	public static void atualizarBD() {
		try {
			SchemaUpdate su = new SchemaUpdate(hibernateConfig);
			su.execute(true, true);
		} catch (Exception e) {
		}
	}

	/**
	 * Retorna todos os registros da tabela (classe) informada
	 * 
	 * @param objClass
	 * @return List<Object>
	 */
	public static List<Object> selecionar(Class objClass) {
		List<Object> lista = null;
		Query query = null;
		try {
			sessao = getSessao();
			transacao = sessao.beginTransaction();
			query = sessao.createQuery("From " + objClass.getName());
			lista = query.list();
		} catch (HibernateException e) {
			transacao.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			sessao.close();
			return lista;
		}
	}

	/**
	 * Retorna apenas um objeto referente a classe e id informados
	 * 
	 * @param objClass
	 * @param id
	 * @return Object
	 */
	public static Object selecionar(Class objClass, long id) {
		Object objGet = null;
		try {
			sessao = getSessao();
			transacao = sessao.beginTransaction();
			objGet = sessao.get(objClass, id);
		} catch (HibernateException e) {
			transacao.rollback();
			System.err.println(e.fillInStackTrace());
		} finally {
			sessao.close();
			return objGet;
		}
	}

	/**
	 * Persiste o objeto passado por parâmetro
	 * 
	 * @param obj
	 */
	public static boolean inserir(Object obj) {
		try {
			sessao = getSessao();
			transacao = sessao.beginTransaction();
			sessao.save(obj);
			transacao.commit();
		} catch (HibernateException e) {
			transacao.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			sessao.close();
			return true;
		}
	}

	/**
	 * Atualiza o objeto passado por parâmetro
	 * 
	 * @param obj
	 */
	public static boolean atualizar(Object obj) {
		try {
			sessao = getSessao();
			transacao = sessao.beginTransaction();
			sessao.update(obj);
			transacao.commit();
		} catch (HibernateException e) {
			transacao.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			sessao.close();
			return true;
		}
	}

	/**
	 * Exclui o objeto passado por parâmetro
	 * 
	 * @param obj
	 */
	public static boolean excluir(Object obj) {
		try {
			sessao = getSessao();
			transacao = sessao.beginTransaction();
			sessao.delete(obj);
			transacao.commit();
		} catch (HibernateException e) {
			transacao.rollback();
			System.err.println(e.fillInStackTrace());
			return false;
		} finally {
			sessao.close();
			return true;
		}
	}
}