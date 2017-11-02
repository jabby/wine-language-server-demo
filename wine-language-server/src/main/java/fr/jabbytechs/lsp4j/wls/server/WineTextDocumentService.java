package fr.jabbytechs.lsp4j.wls.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

import fr.jabbytechs.lsp4j.wls.model.WineDocumentModel;

public class WineTextDocumentService implements TextDocumentService {

	private Map<String, WineDocumentModel> docs = Collections.synchronizedMap(new HashMap<>());
	private final WineLanguageServer wineLanguageServer;

	public WineTextDocumentService(WineLanguageServer wineLanguageServer) {
		this.wineLanguageServer = wineLanguageServer;
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			TextDocumentPositionParams position) {
		WineDocumentModel doc = docs.get(position.getTextDocument().getUri());

		return CompletableFuture.supplyAsync(() -> Either.forLeft(WineCompletionProcessor
				.process(doc.getTypeAttribute(position), doc.getDesignation()).stream().map(word -> {
					CompletionItem item = new CompletionItem();
					item.setLabel(word);
					item.setInsertText(word);
					return item;
				}).collect(Collectors.toList())));

	}

	@Override
	public CompletableFuture<CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		return null;
	}

	@Override
	public CompletableFuture<Hover> hover(TextDocumentPositionParams position) {
		WineDocumentModel doc = docs.get(position.getTextDocument().getUri());

		return CompletableFuture.supplyAsync(() -> {
			Hover res = new Hover();
			res.setContents(WineHoverProcessor.process(doc, position));

			return res;
		});
	}

	@Override
	public CompletableFuture<SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<List<? extends Location>> definition(TextDocumentPositionParams position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<List<? extends Location>> references(ReferenceParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<List<? extends SymbolInformation>> documentSymbol(DocumentSymbolParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<List<? extends Command>> codeAction(CodeActionParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompletableFuture<List<? extends CodeLens>> codeLens(CodeLensParams params) {
		return null;
	}

	@Override
	public CompletableFuture<CodeLens> resolveCodeLens(CodeLens unresolved) {
		return null;
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		return null;
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		return null;
	}

	@Override
	public CompletableFuture<List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		return null;
	}

	@Override
	public CompletableFuture<WorkspaceEdit> rename(RenameParams params) {
		return null;
	}

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		WineDocumentModel model = new WineDocumentModel(params.getTextDocument().getText());
		this.docs.put(params.getTextDocument().getUri(), model);
		CompletableFuture.runAsync(() -> wineLanguageServer.getClient().publishDiagnostics(
				new PublishDiagnosticsParams(params.getTextDocument().getUri(), WineValidator.validate(model))));
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		WineDocumentModel model = new WineDocumentModel(params.getContentChanges().get(0).getText());
		this.docs.put(params.getTextDocument().getUri(), model);
		CompletableFuture.runAsync(() -> wineLanguageServer.getClient().publishDiagnostics(
				new PublishDiagnosticsParams(params.getTextDocument().getUri(), WineValidator.validate(model))));

	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		this.docs.remove(params.getTextDocument().getUri());
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
	}

}
