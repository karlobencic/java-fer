package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * The {@code CalcLayout} class implements {@link Calculator} GUI layout. It
 * uses {@link RCPosition} to arrange the elements in the layout. It has 5 rows
 * and 7 columns, and maximum amount of components is 31. The first component is
 * stretched throughout 5 columns.
 * 
 * @author Karlo Bencic
 * 
 */
public class CalcLayout implements LayoutManager2 {

	/** The number of rows. */
	private static final int ROWS = 5;

	/** The number of columns. */
	private static final int COLUMNS = 7;

	/** The number of max components. */
	private static final int MAX_COMPONENTS = 31;

	/** The layout alignment. */
	private static final float ALIGNMENT = 0.5f;

	/** The first component in the layout. */
	private static final RCPosition FIRST_COMPONENT = new RCPosition(1, 1);

	/** The table of components and their positions. */
	private Map<Component, RCPosition> table = new HashMap<>();

	/** The constraints set. */
	private Set<RCPosition> setConstraints = new HashSet<>();

	/** The offset. */
	private int offset;

	/**
	 * Instantiates a new calc layout.
	 */
	public CalcLayout() {
	}

	/**
	 * Instantiates a new calc layout.
	 *
	 * @param offset
	 *            the offset
	 */
	public CalcLayout(int offset) {
		this.offset = offset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component,
	 * java.lang.Object)
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (table.size() == MAX_COMPONENTS) {
			throw new IllegalArgumentException("Layout is full");
		}

		RCPosition constraint;
		if (constraints instanceof String) {
			String value = (String) constraints;
			try {
				int x = Integer.parseInt(value.split(",")[0]);
				int y = Integer.parseInt(value.split(",")[1]);
				constraint = new RCPosition(x, y);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Illegal string format.");
			}
		} else if (constraints instanceof RCPosition) {
			constraint = (RCPosition) constraints;
		} else {
			throw new IllegalArgumentException("Illegal type of constraints");
		}

		if (!checkConstraint(constraint)) {
			throw new IllegalArgumentException("Illegal constraints value");
		}
		if (!setConstraints.add(constraint)) {
			throw new IllegalArgumentException("Contraint already exists");
		}
		table.put(comp, constraint);
	}

	/**
	 * Checks if the given constraint is valid.
	 *
	 * @param constraint
	 *            the constraint
	 * @return true, if valid
	 */
	private boolean checkConstraint(RCPosition constraint) {
		if (constraint.getRow() < 1 || constraint.getRow() > ROWS) {
			return false;
		}
		if (constraint.getColumn() < 1 || constraint.getColumn() > COLUMNS) {
			return false;
		}
		if (constraint.getRow() == 1 && constraint.getColumn() >= 2 && constraint.getColumn() <= 5) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		RCPosition pos = table.get(comp);
		setConstraints.remove(pos);
		table.remove(comp);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		int[] maxDimensions = new int[2];
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getPreferredSize() != null) {
				getMaximumDimensions(maxDimensions, pos, c.getPreferredSize().width, c.getPreferredSize().height);
			}
		}
		return getLayoutDimension(parent, maxDimensions[1], maxDimensions[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int[] maxDimensions = new int[2];
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getMinimumSize() != null) {
				getMaximumDimensions(maxDimensions, pos, c.getMinimumSize().width, c.getMinimumSize().height);
			}
		}
		return getLayoutDimension(parent, maxDimensions[1], maxDimensions[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		int[] maxDimensions = new int[2];
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getMaximumSize() != null) {
				getMaximumDimensions(maxDimensions, pos, c.getMaximumSize().width, c.getMaximumSize().height);
			}
		}
		return getLayoutDimension(parent, maxDimensions[1], maxDimensions[0]);
	}

	/**
	 * Gets the maximum dimensions.
	 *
	 * @param dimensions
	 *            the dimensions
	 * @param pos
	 *            the position
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return the maximum dimensions
	 */
	private void getMaximumDimensions(int[] dimensions, RCPosition pos, int width, int height) {
		dimensions[0] = Math.max(dimensions[0], height);
		if (!pos.equals(FIRST_COMPONENT)) {
			dimensions[1] = Math.max(dimensions[1], width);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Dimension dim = getLayoutDimension();

			double x = (double) parent.getWidth() / preferredLayoutSize(parent).getWidth();
			double y = (double) parent.getHeight() / preferredLayoutSize(parent).getHeight();
			dim.setSize(x * dim.getWidth(), y * dim.getHeight());

			for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
				setComponentPositionAndSize(entry.getKey(), entry.getValue(), dim);
			}
		}
	}

	/**
	 * Gets the layout dimension.
	 *
	 * @param parent
	 *            the parent container
	 * @param w
	 *            the width
	 * @param h
	 *            the height
	 * @return the layout dimension
	 */
	private Dimension getLayoutDimension(Container parent, int w, int h) {
		return new Dimension(parent.getInsets().left + parent.getInsets().right + COLUMNS * w + (COLUMNS - 1) * offset,
				parent.getInsets().top + parent.getInsets().bottom + ROWS * h + (ROWS - 1) * offset);
	}

	/**
	 * Gets the default layout dimension.
	 *
	 * @return the layout dimension
	 */
	private Dimension getLayoutDimension() {
		int[] maxDimensions = new int[2];
		for (Map.Entry<Component, RCPosition> entry : table.entrySet()) {
			Component c = entry.getKey();
			RCPosition pos = entry.getValue();
			if (c.getPreferredSize() != null) {
				getMaximumDimensions(maxDimensions, pos, c.getPreferredSize().width, c.getPreferredSize().height);
			}
		}
		return new Dimension(maxDimensions[1], maxDimensions[0]);
	}

	/**
	 * Sets the component position and size.
	 *
	 * @param component
	 *            the component
	 * @param position
	 *            the position
	 * @param dim
	 *            the dimension
	 */
	private void setComponentPositionAndSize(Component component, RCPosition position, Dimension dim) {
		if (position.equals(FIRST_COMPONENT)) {
			component.setBounds(0, 0, 5 * dim.width + 4 * offset, dim.height);
			return;
		}
		component.setBounds((position.getColumn() - 1) * (dim.width + offset),
				(position.getRow() - 1) * (dim.height + offset), dim.width, dim.height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentX(Container target) {
		return ALIGNMENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
	 */
	@Override
	public float getLayoutAlignmentY(Container target) {
		return ALIGNMENT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
	 * java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
	 */
	@Override
	public void invalidateLayout(Container target) {
	}
}
