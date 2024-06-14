package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The {@code SmartScriptEngine} class is a custom engine used to execute smart
 * script programs and outputs the result within a request context's output
 * stream.
 * 
 * @author Karlo Bencic
 * 
 */
public class SmartScriptEngine {

	/**
	 * The {@code ParamType} enum represents the types of parameters for
	 * functions that deal with request context.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private enum ParamType {

		/** The normal. */
		NORMAL,
		/** The persistent. */
		PERSISTENT,
		/** The temporary. */
		TEMPORARY
	}

	/** The document node. */
	private final DocumentNode documentNode;

	/** The request context. */
	private final RequestContext requestContext;

	/** The multistack. */
	private final ObjectMultistack multistack = new ObjectMultistack();

	/** The visitor. */
	private final INodeVisitor visitor = new INodeVisitor() {
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.asText());
			} catch (IOException e) {
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
				node.getChild(i).accept(this);
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			String end = node.getEndExpression().asText();
			String step = node.getStepExpression().asText();
			String name = node.getVariable().getName();
			multistack.push(name, start);

			while (start.numCompare(end) <= 0) {
				for (int i = 0, n = node.numberOfChildren(); i < n; i++) {
					node.getChild(i).accept(this);
				}
				multistack.peek(name).add(step);
			}
			multistack.pop(name);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			for (Element el : node.getElements()) {
				if (el instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) el).getValue());
				} else if (el instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) el).getValue());
				} else if (el instanceof ElementString) {
					stack.push(((ElementString) el).getValue());
				} else if (el instanceof ElementVariable) {
					String name = ((ElementVariable) el).getName();
					stack.push(multistack.peek(name).getValue());
				} else if (el instanceof ElementOperator) {
					performOperation(stack, (ElementOperator) el);
				} else if (el instanceof ElementFunction) {
					performFunction(stack, (ElementFunction) el);
				}
			}

			Stack<String> reverse = new Stack<>();
			while (!stack.isEmpty()) {
				reverse.push(stack.pop().toString());
			}
			while (!reverse.isEmpty()) {
				try {
					requestContext.write(reverse.pop());
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Performs the fucntion stored in {@link ElementFunction} token.
		 *
		 * @param stack
		 *            temporary stack with function arguments
		 * @param el
		 *            the element function token with function to be executed
		 */
		private void performFunction(Stack<Object> stack, ElementFunction el) {
			String name = el.getName();

			switch (name.toLowerCase()) {
			case "sin":
				ValueWrapper val = new ValueWrapper(stack.pop());
				Double x = Double.parseDouble(val.getValue().toString()) * Math.PI / 180;
				stack.push(Math.sin(x));
				break;
			case "decfmt":
				DecimalFormat df = new DecimalFormat(stack.pop().toString());
				Object value = new ValueWrapper(stack.pop()).getValue();
				stack.push(df.format(value));
				break;
			case "dup":
				stack.push(stack.peek());
				break;
			case "swap":
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
				break;
			case "setmimetype":
				requestContext.setMimeType(stack.pop().toString());
				break;
			case "paramget":
				getParameter(ParamType.NORMAL, stack);
				break;
			case "pparamget":
				getParameter(ParamType.PERSISTENT, stack);
				break;
			case "pparamset":
				setParameter(ParamType.PERSISTENT, stack);
				break;
			case "pparamdel":
				requestContext.removePersistentParameter(stack.pop().toString());
				break;
			case "tparamget":
				getParameter(ParamType.TEMPORARY, stack);
				break;
			case "tparamset":
				setParameter(ParamType.TEMPORARY, stack);
				break;
			case "tparamdel":
				requestContext.removeTemporaryParameter(stack.pop().toString());
				break;
			}
		}

		/**
		 * Sets a provided parameter in a request context.
		 * 
		 * @param type
		 *            type of the parameter to be set
		 * @param stack
		 *            stack with the parameters
		 */
		private void setParameter(ParamType type, Stack<Object> stack) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			switch (type) {
			case PERSISTENT:
				requestContext.setPersistentParameter(name, value);
				break;
			case TEMPORARY:
				requestContext.setTemporaryParameter(name, value);
				break;
			default:
				break;
			}
		}

		/**
		 * Gets a parameter from a request context and pushes it to a stack.
		 * 
		 * @param type
		 *            type of request parameter
		 * @param stack
		 *            stack with program arguments
		 */
		private void getParameter(ParamType type, Stack<Object> stack) {
			String defaultValue = stack.pop().toString();
			String name = stack.pop().toString();
			String value = null;
			switch (type) {
			case NORMAL:
				value = requestContext.getParameter(name);
				break;
			case PERSISTENT:
				value = requestContext.getPersistentParameter(name);
				break;
			case TEMPORARY:
				value = requestContext.getTemporaryParameter(name);
				break;
			}
			stack.push(value == null ? defaultValue : value);
		}

		/**
		 * Method that performs an operation on two last arguments on the
		 * provided stack.
		 * 
		 * @param stack
		 *            stack with arguments
		 * @param el
		 *            the operator
		 */
		private void performOperation(Stack<Object> stack, ElementOperator el) {
			String symbol = el.getSymbol();
			Object v2 = new ValueWrapper(stack.pop()).getValue();
			ValueWrapper v1 = new ValueWrapper(stack.pop());
			switch (symbol) {
			case "+":
				v1.add(v2);
				break;
			case "-":
				v1.subtract(v2);
				break;
			case "/":
				v1.divide(v2);
				break;
			case "*":
				v1.multiply(v2);
				break;
			}
			stack.push(v1.getValue());
		}
	};

	/**
	 * Instantiates a new smart script engine.
	 *
	 * @param documentNode
	 *            the document node
	 * @param requestContext
	 *            the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes the engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
