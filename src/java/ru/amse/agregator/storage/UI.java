package ru.amse.agregator.storage;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;


public class UI extends JFrame implements TableModelListener{

	private static final long serialVersionUID = 1L;
	
	JTable collectionsTable;
	CollectionsTableModel tableModel = new CollectionsTableModel();
	private JPanel controls;
	JList dbList;
	JList collectionsList; 
	JList typeList;
	
	Container container;
	private String[] dbNames = {Database.MAIN_DB_NAME, Database.DIRTY_DB_NAME};
	public UI() {
		super("Mongo DB Test");

		
		Database.connectToMainBase();
		JMenuItem addItem = new JMenuItem("Add row");
        addItem.addActionListener(new ActionListener() {

                //@Override
				public void actionPerformed(ActionEvent event) {
                        try{
                        tableModel.addRow();
                }catch (Exception e) {
                        System.out.println("Epic Fail");
                }
                
        }});

        JMenuItem deleteItem = new JMenuItem("Delete Row");
        deleteItem.addActionListener(new ActionListener() {
                
                //@Override
				public void actionPerformed(ActionEvent e) {
                        tableModel.removeRow(collectionsTable.getSelectedRow());
                }
        });
        
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(addItem);
        bar.add(deleteItem);

		container = getContentPane();
		container.setLayout(new BorderLayout());
		
		controls = new JPanel();
		controls.setLayout(new GridLayout(3, 1));
		
		dbList = new JList(dbNames);
		dbList.setVisibleRowCount(2);
		dbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(dbList));
		
		collectionsList = new JList();
		collectionsList.setVisibleRowCount(3);
		collectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(collectionsList));
		
		typeList = new JList();
		typeList.setVisibleRowCount(3);
		typeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		controls.add(new JScrollPane(typeList));
		
		collectionsTable = new JTable();
        collectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableModel.addTableModelListener(this);
        container.add(collectionsTable.getTableHeader(), BorderLayout.PAGE_START);
        
        
		dbList.addListSelectionListener(
			new ListSelectionListener(){

			//@Override
			public void valueChanged(ListSelectionEvent event) {
				try{	
					if (dbList.getSelectedIndex() ==0)
						Database.connectToMainBase();
					else
						Database.connectToDirtyBase();
					Vector<String> collectionsNames = new Vector<String>();
					Set<String> collectionsNamesSet = Database.getDB().getCollectionNames();
					for (String collectionName : collectionsNamesSet) {
						collectionsNames.add(collectionName);
					}
					collectionsList.setListData(collectionsNames);
			
					}
					catch (Exception e) {
						System.out.print("ValueChanged!!!!!!!!!!!!");
					}
					}
					
				}
			);
			
		collectionsList.addListSelectionListener(
				new ListSelectionListener(){

				//@Override
				public void valueChanged(ListSelectionEvent event) {
					try{	
						Vector<String> typesNames = new Vector<String>();
						Set<String> typesNamesSet = Database.getTypesNames(collectionsList.getSelectedValue().toString());
						for (String typeName : typesNamesSet) {
							typesNames.add(typeName);
						}
						typeList.setListData(typesNames);
				
						}
						catch (Exception e) {
							System.out.print("ValueChanged!!!!!!!!!!!!");
						}
						}
						
					}
				);
		typeList.addListSelectionListener(
				new ListSelectionListener(){

				//@Override
				public void valueChanged(ListSelectionEvent event) {
					try{	
						container.remove(collectionsTable);
						container.remove(collectionsTable.getTableHeader());
						container.repaint();
						
						Set<String> s = Database.getAttributsNames(typeList.getSelectedValue().toString());
						Vector<String> v = new Vector<String>();
						for (String str : s){
							v.add(str);
						}
						Vector<Vector<Object>> ss = Database.getCollectionValues(typeList.getSelectedValue().toString(),s);
						tableModel.columnNames = v;
						tableModel.rowData = ss;
					 
						collectionsTable = new JTable(tableModel);
						collectionsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						container.add(new JScrollPane(collectionsTable), BorderLayout.CENTER);
						container.add(collectionsTable.getTableHeader(), BorderLayout.PAGE_START);
						container.repaint();
						collectionsTable.revalidate();
						collectionsTable.repaint();
				
						}
						catch (Exception e) {
							System.out.print("ValueChanged!!!!!!!!!!!!\n");
						}
						}
						
					}
				);
		
		
		container.add(controls, BorderLayout.EAST);
		
        container.add(new JScrollPane(collectionsTable), BorderLayout.CENTER);
		setSize(600, 400);
		setVisible(true);

	}
	public static void main(String[] args) {
		(new UI()).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	
	private class CollectionsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		
		public Vector<String> columnNames = new Vector<String>();
        public Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
        {
                for (@SuppressWarnings("unused") Vector<Object> vector : rowData) {
                        vector = new Vector<Object>();
                }
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
                /*int i = 0;
                while( (i < rowData.size())&&( getValueAt(i, columnIndex)==null)) {
                                i++;    
                        }
                if ( i == rowData.size()){
                
                        rowData.get(0).set(columnIndex,"");
                        return getValueAt(0, columnIndex).getClass();
                }
                return getValueAt(i, columnIndex).getClass();*/ 
        	return String.class;
        }

        //@Override
		public int getColumnCount() {
                return columnNames.size();
        }

        @Override
        public String getColumnName(int columnIndex) {
                return columnNames.get(columnIndex);
        }

        //@Override
		public int getRowCount() {
                return rowData.size();
        }

        //@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
                if( (columnIndex >= rowData.get(rowIndex).size()))  return ("777777");
                return rowData.get(rowIndex).get(columnIndex);
                
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
                return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        	 Vector<Object> v = rowData.get(rowIndex);
             v.set(columnIndex, aValue);
             rowData.set(rowIndex, v);
             fireTableCellUpdated(rowIndex, columnIndex);
              
        }
        
        public void addRow() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        	Vector<Object> v = new Vector<Object>();
            
            if (dbList.getSelectedIndex() ==0)
                    Database.connectToMainBase();
            else
                    Database.connectToDirtyBase();
            //String collectionName = collectionsList.getSelectedValue().toString();
            int columnType = 0;
            for ( int i = 0; i < columnNames.size();++i){
                if (columnNames.get(i).equalsIgnoreCase(DBWrapper.FIELD_TYPE)) columnType = i;
            }
            ObjectId id;
            DBWrapper some = new DBWrapper();
            some.setType(typeList.getSelectedValue().toString());
            id = Database.add( some);
            int temp = 0;
            for ( int i = 0; i < columnNames.size();++i){
                    if (columnNames.get(i).equalsIgnoreCase("_id")) temp = i;
            }
            for ( int i = 0; i < columnNames.size(); ++i ){
            	if ( i == temp)
            		v.add(id);
            	else if ( i == columnType)
            		v.add(some.getType());
            	else
            		v.add(null);
            }
           
            rowData.add(v);
            
            collectionsTable.revalidate();
            collectionsTable.repaint();
        }
        
        public void removeRow(int index) {
        	int temp = 0;
            for ( int i = 0; i < columnNames.size();++i){
                    if (columnNames.get(i).equalsIgnoreCase("_id")) temp = i;
            }
    
            ObjectId id =(ObjectId )rowData.get(index).get(temp);
            Database.getDB().getCollection(collectionsList.getSelectedValue().toString()).remove(new BasicDBObject("_id",id));
            rowData.remove(index);
            collectionsTable.revalidate();
            collectionsTable.repaint();
            fireTableRowsDeleted(index, index);
    }
              
        }


	//@Override
	public void tableChanged(TableModelEvent arg0) {	
		
			int row = collectionsTable.getEditingRow(); 
			int column =collectionsTable.getEditingColumn(); 
			int temp_id = 0;
            for ( int i = 0; i < collectionsTable.getColumnCount();++i){
                    if (collectionsTable.getColumnName(i).equalsIgnoreCase("_id")) temp_id = i;
            }
			if (row > -1 && arg0.getType() == TableModelEvent.UPDATE){
				 String temp = collectionsTable.getValueAt(row, column).toString();
				 System.out.println(temp);
				 System.out.println(row); 
				 System.out.println(column);
				 System.out.println(collectionsTable.getColumnName(column));
				
				// Database.setAttribut(collectionsTable.getColumnName(column), temp, collectionsTable.getValueAt(row, temp_id));
			} 
		 	
	}
}
