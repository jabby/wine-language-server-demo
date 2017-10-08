package fr.jabbytechs.lsp4j.wls.server;

import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

public class WineLanguageServer implements LanguageServer {

	private LanguageClient client;
	private TextDocumentService textDocumentService;
	private WorkspaceService workspaceService;

	public WineLanguageServer() {
		this.textDocumentService = new WineTextDocumentService(this);
		this.workspaceService = new WineWorkspaceService();
	}
	
	@Override
	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		final InitializeResult res = new InitializeResult(new ServerCapabilities());
		res.getCapabilities().setCodeActionProvider(Boolean.TRUE);
		res.getCapabilities().setCompletionProvider(new CompletionOptions());
		res.getCapabilities().setDefinitionProvider(Boolean.TRUE);
		res.getCapabilities().setHoverProvider(Boolean.TRUE);
		res.getCapabilities().setReferencesProvider(Boolean.TRUE);
		res.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Full);
		res.getCapabilities().setDocumentSymbolProvider(Boolean.TRUE);
		
		return CompletableFuture.supplyAsync(() -> res);
	}

	@Override
	public CompletableFuture<Object> shutdown() {
		return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
	}

	@Override
	public void exit() {
	}

	@Override
	public TextDocumentService getTextDocumentService() {
		return textDocumentService;
	}

	@Override
	public WorkspaceService getWorkspaceService() {
		return workspaceService;
	}

	/**
	 * Methode permettant de donner le proxy représentant le client du language server.
	 * @param remoteProxy proxy du client
	 */
	public void setRemoteProxy(LanguageClient languageClient) {
		this.client = languageClient;
	}

	public LanguageClient getClient() {
		return client;
	}
}
