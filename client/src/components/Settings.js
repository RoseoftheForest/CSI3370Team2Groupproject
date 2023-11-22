import React from "react";

const Settings = () => {
    const settings = [
        {"name": "Volume", "type": "RangeSlider", "range": [0, 10], "selected": 2},
        {"name": "Text Speed", "type": "ButtonGroup", "values": ["Instant", "Fast", "Normal", "Slow"], "selected": "Normal"},
        {"name": "Text Size", "type": "ButtonGroup", "values": ["Large", "Medium", "Small"], "selected": "Medium"},
        {"name": "Background Color", "type": "ButtonGroup", "values": ["Dark", "Light"], "selected": "Dark"}
    ] // PLACEHOLDER

    let settingsComponent = [];
    for (let i = 0; i < settings.length; i++) {
        let component;
        if (settings[i].type === "RangeSlider") {
            const [ value, setValue ] = React.useState(settings[i].selected)
            const [ finalValue, setFinalValue ] = React.useState(null);
            
            component = <Form>
                <Form.Group as={Row}>
                    <Col xs="9">
                        <RangeSlider 
                            value={value}
                            min={settings[i].range[0]}
                            max={settings[i].range[1]}
                            onChange={e => setValue(e.target.value)}
                            onAfterChange={e => setFinalValue(e.target.value)}
                        />
                    </Col>
                    <Col xs="3">
                        <Form.Control value={finalValue} />
                    </Col>
                </Form.Group>
            </Form>;
        } else if (settings[i].type === "ButtonGroup") {
            const [ selected, setSelected ] = useState(null);
            let btns;
            for (let j = 0; j < settings[i].values.length; j++) {
                if (settings[i].values[j] === settings[i].selected) {
                    setSelected(j)
                }
                
                btns += <Button
                    color="primary"
                    outline
                    onClick={() => setSelected(j)}
                    active={selected === j}
                >
                    {settings[i].values[j]}
                </Button>
            }
            component = <div><ButtonGroup>{btns}</ButtonGroup><p>Selected: {selected}</p></div>
        }

        settingsComponent += component;
        
    }
    
    return (
        {settingsComponent}
    )
};

export default Settings;