import React from 'react'
import { useState, useEffect } from 'react'
import { Badge, Card, Col, Row } from 'react-bootstrap'
import DataTable from 'react-data-table-component'
import ButtomCircle from '../../shared/components/ButtomCircle'
import Loading from '../../shared/components/Loading'
import FilterComponent from '../../shared/components/FilterComponent'
import AxiosClient from '../../shared/plugins/axios';
import CategoryForm from './CategoryForm'

const options = {
    rowsPerPageText: 'Registros por página',
    rangeSeparatorText: 'de',
}

const CategoryScreen = () => {
    const [categories, setCategories] =useState([]);
    const [selectedCategory, setSelectedCategory] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [filterText, setFilterText] = useState("");
    const [isOpen, setIsOpen] = useState(false);
    
    const filteredCategories = categories.filter(
        category => category.name && category.name.toLowerCase().includes(filterText.toLowerCase)
    );

    const getCategories = async () =>{
        try {
            setIsLoading(true)
            const data = await AxiosClient({url: '/category/'})
            if (!data.error) setCategories(data.data)

        } catch (error) {
            //poner alerta de error
            setIsLoading(false)
        }
    };

    useEffect(()=>{
        getCategories();
    }, []);

    const headerComponent = React.useMemo(() => {
        const handleClear = () => {
            if (filterText) {
                setFilterText("");
            }
        };
        return (
            <FilterComponent onFilter={(e) => setFilterText(e.target.value)} onClear={handleClear} filterText={filterText} />
        );
    }, [filterText]);

    const columns = React.useMemo(() => [
        {
            name: "#",
            cell: (row, index) => <div>{index + 1}</div>,
            sortable: true,
        },
        {
            name: "Categoria",
            cell: (row) => <div>{row.name}</div>,
            sortable: true,
            selector: (row) => row.name,
        },
        {
            name: "Estado",
            cell: (row) => row.status ? (<Badge bg="sucess">Activo</Badge>) : (<Badge bg="danger">Inactivo</Badge>),
            sortable: true,
            selector: (row) => row.status,
        },
        {
            name: "Acciones",
            cell: (row) => <>
                <ButtomCircle icon="edit" type={"btn btn-outline-warning btn-circle"} size={16} onClick={() => {setIsEditing(true); setSelectedCategory(row); } } />
                {
                    row.status ? (<ButtomCircle icon="trash-2"  type={"btn btn-outline-danger btn-circle"} size={16} onClick={() => {}}/>) : (<ButtomCircle icon="pocket"  type={"btn btn-outline-success btn-circle"} size={16} onClick={() => {}}/>)
                }
            </>
        }
    ]);
    
  return (
   <Card>
    <Card.Header>
        <Row>
            <Col>Categorias</Col>
            <Col className='text-end'>
                <ButtomCircle type={"btn btn-outline-success"} onClick={() =>{}} icon="plus" size={16} />
                <CategoryForm isOpen={isOpen} onClose={()=>setIsOpen(false)} />
            </Col>
        </Row>
    </Card.Header>
    <Card.Body>
        <DataTable
            columns={[columns]}
            data={filteredCategories}
            progressPending={isLoading}
            progressComponent={<Loading/>}
            noDataComponent={"Sin registros"}
            pagination
            paginationComponentOptions={options}
            subHeader
            subHeaderComponent={headerComponent}
            persistTableHead
            striped={true}
            highlightOnHover={true}
        />
    </Card.Body>
   </Card>
  )
}

export default CategoryScreen
