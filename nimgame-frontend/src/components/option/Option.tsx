import styles from "./Option.module.css";

type OptionProps = {
  onChange: (isChecked: boolean) => void;
  optionName: string;
};

export function Option(props: OptionProps) {
  return (
    <div className={styles.Option}>
      <input
        className={styles.Checkbox}
        name={props.optionName}
        type="checkbox"
        onChange={(e) => props.onChange(e.target.checked)}
      />
      <label htmlFor={props.optionName} className={styles.Label}>
        {props.optionName}
      </label>
    </div>
  );
}
